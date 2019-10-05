package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.snakeskin.measure.Seconds
import org.team401.brb2019.LEDManager
import org.team401.brb2019.constants.PowerConstants
import org.team401.brb2019.constants.HardwareMap

object CargoSubsystem : Subsystem() {
    private val wheelsTalon = TalonSRX(HardwareMap.cargoIntakeTalonId)
    private val beltTalon = TalonSRX(HardwareMap.cargoBeltTalonId)

    private val intakeSolenoid = Solenoid(HardwareMap.cargoIntakeSolenoidId)

    private val ballSensor = DigitalInput(HardwareMap.ballSensorInputId)
    private val ballSensorComplement = DigitalInput(HardwareMap.ballSensorInputIdComplement)

    enum class BallSensorState {
        Fault,
        BallAbsent,
        BallPresent
    }

    private fun getBallSensorState(): BallSensorState {
        val sensor = ballSensor.get()
        val complement = ballSensorComplement.get()
        if (sensor == complement) return BallSensorState.Fault

        return if (sensor) BallSensorState.BallAbsent
        else BallSensorState.BallPresent
    }

    enum class CargoStates {
        Intaking,
        Stowed,
        Shuttling,
        Scoring,
        Reverse
    }

    val cargoMachine: StateMachine<CargoStates> = stateMachine {
        state(CargoStates.Intaking) {
            entry {
                intakeSolenoid.set(true)
            }

            action {
                wheelsTalon.set(ControlMode.PercentOutput, PowerConstants.cargoIntakePower)
                beltTalon.set(ControlMode.PercentOutput, PowerConstants.beltIntakePower)
                if (getBallSensorState() == BallSensorState.BallPresent) {
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

        state(CargoStates.Reverse) {
            entry {
                intakeSolenoid.set(false)
            }

            action {
                beltTalon.set(ControlMode.PercentOutput, -PowerConstants.beltIntakePower)
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
            }
        }

        state(CargoStates.Shuttling) {
            timeout(2.0.Seconds, CargoStates.Stowed)

            entry {
                intakeSolenoid.set(false)
                val sensorState = getBallSensorState()
                if (sensorState == BallSensorState.Fault || sensorState == BallSensorState.BallPresent) {
                    LEDManager.signalIntake()
                    setState(CargoStates.Stowed)
                }
            }

            action {
                beltTalon.set(ControlMode.PercentOutput, PowerConstants.beltShuttlingPower)
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
                val sensorState = getBallSensorState()
                if (sensorState == BallSensorState.Fault || sensorState == BallSensorState.BallPresent) {
                    LEDManager.signalIntake()
                    setState(CargoStates.Stowed)
                }
            }
        }

        state(CargoStates.Scoring) {
            entry {
                intakeSolenoid.set(false)
            }
            action {
                beltTalon.set(ControlMode.PercentOutput, PowerConstants.beltScoringPower)
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
            }
        }
    }

    override fun setup() {
        beltTalon.setNeutralMode(NeutralMode.Brake)
        on(Events.TELEOP_ENABLED) {
            cargoMachine.setState(CargoStates.Stowed)
        }
    }

}