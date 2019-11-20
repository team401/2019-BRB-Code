package org.team401.brb2019.auto.steps

import org.snakeskin.auto.steps.AutoStep
import org.team401.brb2019.subsystems.DrivetrainSubsystem
import org.team401.taxis.geometry.Pose2d
import org.team401.taxis.trajectory.TimedView
import org.team401.taxis.trajectory.TrajectoryIterator
import org.team401.taxis.trajectory.timing.CentripetalAccelerationConstraint

class DrivePath(val waypoints: List<Pose2d>) : AutoStep() {

    val trajectory = DrivetrainSubsystem.pathManager.generateTrajectory(
        false,
        waypoints,
        listOf(CentripetalAccelerationConstraint(110.0)),
        6.0 * 12.0,
        6.0 * 12.0,
        9.0
    )

    override fun entry(currentTime: Double) {
        DrivetrainSubsystem.setPose(waypoints.first())
        DrivetrainSubsystem.pathManager.reset()
        DrivetrainSubsystem.pathManager.setTrajectory(TrajectoryIterator(TimedView(trajectory)))
        DrivetrainSubsystem.driveMachine.setState(DrivetrainSubsystem.States.PathFollowing)
    }

    override fun action(currentTime: Double, lastTime: Double): Boolean {
        return DrivetrainSubsystem.pathManager.isDone
    }

    override fun exit(currentTime: Double) {

    }
}