package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.snakeskin.measure.Seconds
import org.team401.brb2019.constants.HardwareMap

object CargoSubsystem : Subsystem() {
    private val wheelsTalon = TalonSRX(HardwareMap.cargoIntakeTalonId)
    private val beltTalon = TalonSRX(HardwareMap.cargoBeltTalonId)

    private val intakeSolenoid = Solenoid(HardwareMap.cargoIntakeSolenoidId)

    private val ballSensor = DigitalInput(HardwareMap.ballSensorInputId)

    enum class CargoStates {
        Intaking,
        Stowed,
        Shuttling,
        Scoring,
    }

    val cargoMachine: StateMachine<CargoStates> = stateMachine {
        state(CargoStates.Intaking) {
            entry {
                intakeSolenoid.set(true)
            }

            action {
                wheelsTalon.set(ControlMode.PercentOutput, 1.0)
                beltTalon.set(ControlMode.PercentOutput, 1.0)
                if (ballSensor.get()) {
                    setState(CargoStates.Shuttling)
                }
            }

        }

        state(CargoStates.Stowed) {
            entry {
                intakeSolenoid.set(false)
            }

            action {
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
                beltTalon.set(ControlMode.PercentOutput, 0.0)
            }
        }

        state(CargoStates.Shuttling) {
            entry {
                intakeSolenoid.set(false)
                if (ballSensor.get()) {
                    setState(CargoStates.Stowed)
                }
            }

            timeout(5.0.Seconds, CargoStates.Stowed)
            action {
                beltTalon.set(ControlMode.PercentOutput, 1.0)
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
                if (ballSensor.get()) {
                    setState(CargoStates.Stowed)
                }
            }
        }

        state(CargoStates.Scoring) {
            entry {
                intakeSolenoid.set(false)
            }
            action {
                beltTalon.set(ControlMode.PercentOutput, 1.0)
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
            }
        }
    }

    override fun setup() {
        on(Events.TELEOP_ENABLED) {
            cargoMachine.setState(CargoStates.Stowed)
        }
    }

}