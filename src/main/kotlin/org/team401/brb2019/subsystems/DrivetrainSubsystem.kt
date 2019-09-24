package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.snakeskin.component.IDifferentialDrivetrain
import org.snakeskin.component.impl.CTRESmartGearbox
import org.snakeskin.component.impl.DifferentialDrivetrain
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
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

    val driveMachine: StateMachine<States> = stateMachine {
        state(States.OperatorControl) {
            action {
                val translation = LeftStick.readAxis { PITCH }
                val rotation = RightStick.readAxis { ROLL }
                arcade(translation, rotation)
            }
        }
    }

    override fun setup() {
        on(Events.ENABLED) {
            driveMachine.setState(States.OperatorControl)
        }
    }
}