package org.team401.brb2019.auto.steps
import com.ctre.phoenix.motorcontrol.ControlMode
import org.snakeskin.auto.steps.AutoStep
import org.team401.brb2019.subsystems.DrivetrainSubsystem
import com.ctre.phoenix.sensors.PigeonIMU
import kotlin.math.abs

class Drive(): AutoStep() {
    val left = DrivetrainSubsystem.left.master
    val right = DrivetrainSubsystem.right.master

    var pigeonAngle = DrivetrainSubsystem.getHeadingDegrees()


    private val leftTargetRotations = 1
    private val rightTargetRotations = 1

    //set to actual values later
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

        //determine motor change percents, use PID later
        var motorIncreasePercent = motorIncreasePercentConstant
        var motorDecreasePercent = motorDecreasePercentConstant

        if (pigeonAngle < 0) {
            leftPower = (1 + motorIncreasePercent) * leftPower
            rightPower = (1 - motorDecreasePercent) * rightPower
        }
        else {
            leftPower = (1 - motorDecreasePercent) * leftPower
            rightPower = (1 + motorIncreasePercent) * rightPower
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