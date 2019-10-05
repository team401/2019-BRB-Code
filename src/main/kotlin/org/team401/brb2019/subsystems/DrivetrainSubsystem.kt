package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.snakeskin.component.IDifferentialDrivetrain
import org.snakeskin.component.impl.CTRESmartGearbox
import org.snakeskin.component.impl.DifferentialDrivetrain
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.snakeskin.logic.scalars.Scalar
import org.snakeskin.logic.scalars.ScalarGroup
import org.snakeskin.logic.scalars.SquareScalar
import org.snakeskin.measure.Seconds
import org.snakeskin.utility.CheesyDriveController
import org.team401.brb2019.LeftStick
import org.team401.brb2019.RightStick
import org.team401.brb2019.constants.DrivetrainGeometry
import org.team401.brb2019.constants.HardwareMap

object DrivetrainSubsystem: Subsystem(), IDifferentialDrivetrain<CTRESmartGearbox<TalonSRX>> by DifferentialDrivetrain(
    CTRESmartGearbox(TalonSRX(HardwareMap.leftDriveFrontTalonId), TalonSRX(HardwareMap.leftDriveRearTalonId)),
    CTRESmartGearbox(TalonSRX(HardwareMap.rightDriveFrontTalonId), TalonSRX(HardwareMap.rightDriveRearTalonId)),
    DrivetrainGeometry
) {
    enum class States {
        OperatorControl
    }

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
    }

    override fun setup() {
        both {
            master.setNeutralMode(NeutralMode.Brake)
            setCurrentLimit(40.0, 100.0, 0.1.Seconds)
            setRampRate(0.25)
            slaves.forEach {
                it.setNeutralMode(NeutralMode.Brake)
            }
        }

        left.inverted = false
        right.inverted = true

        on(Events.ENABLED) {
            driveMachine.setState(States.OperatorControl)
        }
    }
}