package org.team401.brb2019.auto

import org.snakeskin.auto.RobotAuto
import org.snakeskin.auto.steps.SequentialSteps
import org.snakeskin.dsl.auto
import org.team401.brb2019.auto.steps.CorrectedDrive

object EverybotAutoRoutine: RobotAuto(20L) {

    override fun assembleAuto(): SequentialSteps {
        return auto {
            step(CorrectedDrive(10.0))
        }
    }
}