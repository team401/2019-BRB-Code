package org.team401.brb2019.subsystems

import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*;
import org.snakeskin.event.Events

object HatchSubsystem : Subsystem() {
    val pusherSolenoid = Solenoid(0)

    enum class PusherStates {
        In,
        Out
    }

    val pusherMachine: StateMachine<PusherStates> = stateMachine {
        state(PusherStates.In){
            entry {
                pusherSolenoid.set(false)
            }
        }

        state(PusherStates.Out){
            entry{
                pusherSolenoid.set(true)
            }
        }
    }

    override fun setup() {
        on(Events.TELEOP_ENABLED){
            pusherMachine.setState(HatchSubsystem.PusherStates.In)
        }
    }
}