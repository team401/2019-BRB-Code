package org.team401.brb2019.auto

import org.snakeskin.auto.RobotAuto
import org.snakeskin.auto.steps.SequentialSteps
import org.snakeskin.dsl.auto
import org.team401.brb2019.auto.steps.DrivePath
import org.team401.taxis.geometry.Pose2d
import org.team401.taxis.geometry.Rotation2d

object EverybotAutoRoutine: RobotAuto(10L) {

    override fun assembleAuto(): SequentialSteps {
        return auto {
            //step(CorrectedDrive(10.0))
            //step(TuneWheelRadius(DrivetrainSubsystem, 24.0.Inches))
            //step(TuneTrackScrubFactor(DrivetrainSubsystem, power = .5))
            //step(CollectDynamicsData(DrivetrainSubsystem))
            step(DrivePath(
                listOf(
                    Pose2d.identity(),
                    Pose2d(6.0 * 12, 0.0, Rotation2d.fromDegrees(0.0)),
                    Pose2d(10.0 * 12, 4.0 * 12.0, Rotation2d.fromDegrees(90.0)),
                    Pose2d(10.0 * 12, 8.0 * 12, Rotation2d.fromDegrees(90.0))
                )
            ))
        }
    }
}