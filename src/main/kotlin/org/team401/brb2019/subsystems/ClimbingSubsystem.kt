package org.team401.brb2019.subsystems

import com.ctre.phoenix.sensors.PigeonIMU
import edu.wpi.first.wpilibj.Encoder
import org.snakeskin.dsl.*
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.event.Events
import org.team401.brb2019.constants.HardwareMap

object ClimbingSubsystem: Subsystem() {
    private val backPiston = Solenoid(HardwareMap.climbingBackSolenoidId)
    private val frontPiston = Solenoid(HardwareMap.climbingFrontSolenoidId)

    enum class ClimbingStates {
        Stowed,
        Extended
    }

    val frontMachine: StateMachine<ClimbingStates> = stateMachine() {
        state(ClimbingStates.Extended) {
            entry {
                frontPiston.set(true)
            }
        }

        state(ClimbingStates.Stowed) {
            entry {
                frontPiston.set(false)
            }
        }
    }

    val backMachine: StateMachine<ClimbingStates> = stateMachine {
        state(ClimbingStates.Extended) {
            entry {
                backPiston.set(true)
            }
        }

        state(ClimbingStates.Stowed) {
            entry {
                backPiston.set(false)
            }
        }
    }

    override fun setup() {
        on(Events.ENABLED) {
            frontMachine.setState(ClimbingStates.Stowed)
            backMachine.setState(ClimbingStates.Stowed)
        }
    }
}