package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*;
import org.snakeskin.event.Events

object HatchSubsystem : Subsystem() {
    private val pusherSolenoid = Solenoid(0)
    private val wheelsTalon = TalonSRX(0)

    enum class States {
        Stowed,
        Holding,
        Intaking,
        Scoring
    }

    val hatchMachine: StateMachine<States> = stateMachine {
        state(HatchSubsystem.States.Stowed) {

        }

        state(HatchSubsystem.States.Holding) {

        }

        state(HatchSubsystem.States.Intaking) {

        }

        state(HatchSubsystem.States.Scoring) {

        }

    }

    override fun setup() {
        on(Events.TELEOP_ENABLED){
        }
    }
}