package org.team401.brb2019.auto.steps
import com.ctre.phoenix.motorcontrol.ControlMode
import org.snakeskin.auto.steps.AutoStep
import org.team401.brb2019.subsystems.DrivetrainSubsystem
import kotlin.math.abs

class Drive(): AutoStep() {
    private val left = DrivetrainSubsystem.left.master
    private val right = DrivetrainSubsystem.right.master

    var pigeonAngle = DrivetrainSubsystem.getHeadingDegrees()

    private val leftTargetRotations = 1
    private val rightTargetRotations = 1

    private val motorIncreasePercentConstant = 0.3
    private val motorDecreasePercentConstant = 0.05

    //Will be changed
    private val pigeonErrorThreshold = 0.0

    private var leftPower = 0.5
    private var rightPower = 0.5

    private var leftRotations = 0
    private var rightRotations = 0

    private var leftAtDist = false
    private var rightAtDist = false

    override fun entry(currentTime: Double) {
        leftRotations = 0
        rightRotations = 0
    }

    override fun action(currentTime: Double, lastTime: Double): Boolean {
        leftRotations = left.selectedSensorPosition / 4096
        rightRotations = right.selectedSensorPosition / 4096

    if (abs(pigeonAngle) > pigeonErrorThreshold) {

        if (pigeonAngle < 0) {
            leftPower *= (1 + motorDecreasePercentConstant)
            rightPower *= (1 - motorDecreasePercentConstant)
        }
        else {
            leftPower *= (1 - motorDecreasePercentConstant)
            rightPower *= (1 + motorIncreasePercentConstant)
        }
    }

        left.set(ControlMode.PercentOutput, leftPower)
        right.set(ControlMode.PercentOutput, rightPower)

        leftAtDist = (leftRotations >= leftTargetRotations)
        rightAtDist = (rightRotations >= rightTargetRotations)

        return (leftAtDist && rightAtDist)
    }

    override fun exit(currentTime: Double) {

    }
}