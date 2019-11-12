package org.team401.brb2019.auto.steps
import com.ctre.phoenix.motorcontrol.ControlMode
import org.snakeskin.auto.steps.AutoStep
import org.team401.brb2019.subsystems.DrivetrainSubsystem

class CorrectedDrive(val targetDistance: Double): AutoStep() {
    private val left = DrivetrainSubsystem.left.master
    private val right = DrivetrainSubsystem.right.master

    var pigeonAngle = 0.0 //DrivetrainSubsystem.getHeadingDegrees()

    private var correctionFactor = 0.0

    private var leftPower = 0.5
    private var rightPower = 0.5
    private var basePower = 0.9

    private val angleKp = 0.5
    private val distanceKp = 0.25

    private var leftAtDist = false
    private var rightAtDist = false

    override fun entry(currentTime: Double) {
        //DrivetrainSubsystem.setHeading(0.0)
        DrivetrainSubsystem.driveMachine.setState(DrivetrainSubsystem.States.ExternalControl)
        left.setSelectedSensorPosition(0, 0, 1000)
        right.setSelectedSensorPosition(0, 0, 1000)
    }

    override fun action(currentTime: Double, lastTime: Double): Boolean {
        val leftRotations = left.selectedSensorPosition / 4096.0
        val rightRotations = right.selectedSensorPosition / 4096.0

        val dist = (leftRotations + rightRotations) / 2.0

        val error = targetDistance - dist
        val distancePower = error * distanceKp

        pigeonAngle = 0.0//DrivetrainSubsystem.getHeadingDegrees()
        correctionFactor = pigeonAngle * angleKp

        leftPower = basePower - correctionFactor
        rightPower = basePower + correctionFactor

        left.set(ControlMode.PercentOutput, leftPower)
        right.set(ControlMode.PercentOutput, rightPower)

        return (error <= 0.5)
    }

    override fun exit(currentTime: Double) {
        left.set(ControlMode.PercentOutput, 0.0)
        right.set(ControlMode.PercentOutput, 0.0)
    }
}