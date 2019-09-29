package org.team401.brb2019.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Solenoid
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.snakeskin.measure.Milliseconds
import org.snakeskin.measure.Seconds
import org.snakeskin.utility.Ticker
import org.team401.brb2019.constants.CargoBeltConstants
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
    }

    val cargoMachine: StateMachine<CargoStates> = stateMachine {
        state(CargoStates.Intaking) {
            entry {
                intakeSolenoid.set(true)
            }

            action {
                wheelsTalon.set(ControlMode.PercentOutput, 1.0)
                beltTalon.set(ControlMode.PercentOutput, CargoBeltConstants.intakingPower)
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

        state(CargoStates.Shuttling) {
            timeout(2.0.Seconds, CargoStates.Stowed)

            entry {
                intakeSolenoid.set(false)
                val sensorState = getBallSensorState()
                if (sensorState == BallSensorState.Fault || sensorState == BallSensorState.BallPresent) {
                    setState(CargoStates.Stowed)
                }
            }

            action {
                beltTalon.set(ControlMode.PercentOutput, CargoBeltConstants.shuttlingPower)
                wheelsTalon.set(ControlMode.PercentOutput, 0.0)
                val sensorState = getBallSensorState()
                if (sensorState == BallSensorState.Fault || sensorState == BallSensorState.BallPresent) {
                    setState(CargoStates.Stowed)
                }
            }
        }

        state(CargoStates.Scoring) {
            entry {
                intakeSolenoid.set(false)
            }
            action {
                beltTalon.set(ControlMode.PercentOutput, CargoBeltConstants.scoringPower)
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