package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.RobotController
import org.snakeskin.component.TalonPigeonIMU
import org.snakeskin.component.impl.CTRESmartGearbox
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.snakeskin.logic.scalars.Scalar
import org.snakeskin.logic.scalars.ScalarGroup
import org.snakeskin.logic.scalars.SquareScalar
import org.snakeskin.measure.Radians
import org.snakeskin.measure.RevolutionsPerMinute
import org.snakeskin.measure.Seconds
import org.snakeskin.utility.CheesyDriveController
import org.team401.brb2019.LeftStick
import org.team401.brb2019.RightStick
import org.team401.brb2019.constants.DrivetrainDynamics
import org.team401.brb2019.constants.DrivetrainGeometry
import org.team401.brb2019.constants.HardwareMap
import org.team401.taxis.diffdrive.component.IPathFollowingDiffDrive
import org.team401.taxis.diffdrive.component.impl.PigeonPathFollowingDiffDrive
import org.team401.taxis.diffdrive.control.NonlinearFeedbackPathController
import org.team401.taxis.diffdrive.odometry.OdometryTracker
import org.team401.taxis.geometry.Pose2d

object DrivetrainSubsystem: Subsystem(), IPathFollowingDiffDrive<CTRESmartGearbox<TalonSRX>> by PigeonPathFollowingDiffDrive(
    CTRESmartGearbox(TalonSRX(HardwareMap.leftDriveRearTalonId), TalonSRX(HardwareMap.leftDriveFrontTalonId)),
    CTRESmartGearbox(TalonSRX(HardwareMap.rightDriveRearTalonId), TalonSRX(HardwareMap.rightDriveFrontTalonId)),
    TalonPigeonIMU.create(HardwareMap.leftDriveFrontTalonId),
    DrivetrainGeometry,
    DrivetrainDynamics,
    NonlinearFeedbackPathController(2.0, 0.7)
) {
    enum class States {
        OperatorControl,
        ExternalControl,
        MeasureEff,
        PathFollowing
    }

    val stateEstimator = OdometryTracker(this)

    private val freeSpeedRPM = (5840 / 10.75).RevolutionsPerMinute

    private val cheesyController = CheesyDriveController(object : CheesyDriveController.DefaultParameters() {
        override val quickTurnScalar = ScalarGroup(SquareScalar, object : Scalar {
            override fun scale(input: Double): Double {
                return input / 3.33
            }
        })
    })

    val driveMachine: StateMachine<States> = stateMachine {
        state(States.OperatorControl) {
            entry {
                cheesyController.reset()
                both{
                    setRampRate(0.25)
                }
            }
            action {
                /*
                val output = cheesyController.update(
                    LeftStick.readAxis { PITCH },
                    RightStick.readAxis { ROLL },
                    false,
                    RightStick.readButton { TRIGGER }
                )
                tank(output.left, output.right)
                */
                 arcade(
                     LeftStick.readAxis { PITCH },
                     RightStick.readAxis { ROLL } * .75
                 )
            }
        }

        state(States.MeasureEff){
            action {
                left.set(1.0)
                right.set(1.0)

                val leftRPM = left.getVelocity().toRevolutionsPerMinute()
                val rightRPM = right.getVelocity().toRevolutionsPerMinute()

                println("Left: ${(leftRPM / freeSpeedRPM).value * 100.0}  Right: ${(rightRPM / freeSpeedRPM).value * 100.0}")
            }
        }

        state(States.PathFollowing) {
            entry {
                both {
                    setDeadband(0.0)
                    setRampRate(0.0)
                }
            }

            rtAction {
                val output = pathManager.update(time, driveState.getFieldToVehicle(time))
                val vbus = RobotController.getBatteryVoltage()
                val leftOut = output.left_feedforward_voltage / vbus
                val rightOut = output.right_feedforward_voltage / vbus

                tank(leftOut, rightOut)
            }

            exit {
                stop()
            }
        }
    }

    override fun action () {
        //println(driveState.getLatestFieldToVehicle().value)
    }

    override fun setup() {
        both {
            master.setNeutralMode(NeutralMode.Brake)
            setCurrentLimit(40.0, 100.0, 0.1.Seconds)
            setRampRate(0.25)
            master.setSensorPhase(true)
            master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
            master.setControlFramePeriod(ControlFrame.Control_3_General, 5)
            slaves.forEach {
                it.setNeutralMode(NeutralMode.Brake)
            }
        }

        left.inverted = false
        right.inverted = true

        both {
            setPosition(0.0.Radians)
            master.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 5, 100)
            master.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_50Ms, 100)
            master.configVelocityMeasurementWindow(1, 100)
        }

        setPose(Pose2d.identity())

        on(Events.TELEOP_ENABLED) {
            driveMachine.setState(States.OperatorControl)
            setPose(Pose2d.identity())

        }
    }
}