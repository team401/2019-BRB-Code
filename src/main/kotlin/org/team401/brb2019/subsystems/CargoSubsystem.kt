package org.team401.brb2019.subsystems

import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*
import org.snakeskin.event.Events

object CargoSubsystem : Subsystem() {
    private val intakeSolenoid = Solenoid(1)

    enum class IntakeStates {
        Intaking,
        Stowed
    }

    val intakeMachine: StateMachine<IntakeStates> = stateMachine {
        state(CargoSubsystem.IntakeStates.Intaking) {
           entry {
               intakeSolenoid.set(true)
           }
        }

        state(CargoSubsystem.IntakeStates.Stowed) {
            entry {
                intakeSolenoid.set(false)
            }
        }
    }

    override fun setup() {
        on(Events.TELEOP_ENABLED) {
            intakeMachine.setState(CargoSubsystem.IntakeStates.Stowed)
        }
    }

}