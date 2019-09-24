package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*
import org.snakeskin.event.Events

object CargoSubsystem : Subsystem() {
    private val wheelsTalon = TalonSRX(1)
    private val beltTalon = TalonSRX(2)

    private val intakeSolenoid = Solenoid(1)

    enum class States {
        Intaking,
        Stowed,
        Shuttling,
        Scoring,
    }

    val cargoMachine: StateMachine<States> = stateMachine {
        state(States.Intaking) {

        }

        state(States.Stowed) {

        }

        state(States.Shuttling) {

        }

        state(States.Scoring) {

        }
    }



    override fun setup() {
        on(Events.TELEOP_ENABLED) {
        }
    }

}