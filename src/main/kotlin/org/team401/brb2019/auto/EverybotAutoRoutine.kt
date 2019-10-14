package org.team401.brb2019.auto

import org.snakeskin.auto.RobotAuto
import org.snakeskin.auto.steps.SequentialSteps
import org.snakeskin.dsl.auto
import org.snakeskin.measure.Seconds
import org.team401.brb2019.auto.steps.DriveSteps
import org.team401.brb2019.auto.steps.SubsystemHomeStep
import org.team401.brb2019.subsystems.CargoSubsystem
import org.team401.brb2019.subsystems.HatchSubsystem

object EverybotAutoRoutine: RobotAuto(20L) {

    override fun assembleAuto(): SequentialSteps {
        return auto {
            step(SubsystemHomeStep())
            step(DriveSteps(1.0, 0.5, "FORWARD"))
            step(DriveSteps(0.5, 0.5, "LEFT"))
        }
    }
}