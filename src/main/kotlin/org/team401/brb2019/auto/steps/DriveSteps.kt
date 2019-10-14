package org.team401.brb2019.auto.steps

import com.ctre.phoenix.motorcontrol.ControlMode
import org.snakeskin.auto.steps.AutoStep
import org.team401.brb2019.subsystems.DrivetrainSubsystem

class DriveSteps(val time: Double, val power: Double, val action: String): AutoStep() {
    val left = DrivetrainSubsystem.left.master
    val right = DrivetrainSubsystem.right.master
    private var entryTime = 0.0
    override fun entry(currentTime: Double) {
        entryTime = currentTime
    }
    override fun action(currentTime: Double, lastTime: Double): Boolean {
        when (action) {
            "FORWARD" -> {
                left.set(ControlMode.PercentOutput, power)
                right.set(ControlMode.PercentOutput, power)
                return (currentTime - entryTime) >= time
            }
            "BACKWARD" -> {
                left.set(ControlMode.PercentOutput, -power)
                right.set(ControlMode.PercentOutput, -power)
                return (currentTime - entryTime) >= time
            }
            "RIGHT" -> {
                left.set(ControlMode.PercentOutput, power)
                right.set(ControlMode.PercentOutput, -power)
                return (currentTime - entryTime) >= time
            }
            "LEFT" -> {
                left.set(ControlMode.PercentOutput, -power)
                right.set(ControlMode.PercentOutput, power)
                return (currentTime - entryTime) >= time
            }
            else -> return false
        }
    }

    override fun exit(currentTime: Double) {
        left.set(ControlMode.PercentOutput, 0.0)
        right.set(ControlMode.PercentOutput, 0.0)
    }
}
