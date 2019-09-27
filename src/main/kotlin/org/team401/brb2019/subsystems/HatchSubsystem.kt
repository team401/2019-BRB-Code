package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*;
import org.snakeskin.event.Events
import org.team401.brb2019.constants.HardwareMap

object HatchSubsystem : Subsystem() {
    private val pusherSolenoid = Solenoid(HardwareMap.hatchPusherSolenoidId)
    private val wheelsTalon = TalonSRX(HardwareMap.hatchWheelsTalonId)

    enum class States {
        Stowed,
        Holding,
        Intaking,
        Scoring
    }

    enum class PusherStates {
        Stowed,
        Deployed
    }

    val pusherMachine: StateMachine<PusherStates> = stateMachine {
        state(HatchSubsystem.PusherStates.Stowed) {
            rejectIf { hatchMachine.isInState(States.Intaking) }
            entry {
                pusherSolenoid.set(false)
            }
        }

        state(HatchSubsystem.PusherStates.Deployed) {
            entry {
                pusherSolenoid.set(true)
            }
        }
    }

    val hatchMachine: StateMachine<States> = stateMachine {
        state(HatchSubsystem.States.Stowed) {
            entry{
                pusherMachine.setState(PusherStates.Stowed)
            }
            action{
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
            }
        }

        state(HatchSubsystem.States.Holding) {
            entry{
                pusherMachine.setState(PusherStates.Stowed)
            }
            action{
                wheelsTalon.set(ControlMode.PercentOutput, -0.25)
            }
        }

        state(HatchSubsystem.States.Intaking) {
            entry{
                pusherMachine.setState(PusherStates.Deployed)
            }
            action{
                wheelsTalon.set(ControlMode.PercentOutput, -1.0)
            }
        }

        state(HatchSubsystem.States.Scoring) {
            action{
                wheelsTalon.set(ControlMode.PercentOutput, 1.0)
            }
        }

    }

    override fun setup() {
        on(Events.ENABLED){
            hatchMachine.setState(HatchSubsystem.States.Stowed)
        }
    }
}