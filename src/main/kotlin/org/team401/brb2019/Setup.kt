package org.team401.brb2019

import org.snakeskin.auto.AutoManager
import org.snakeskin.controls.ControlPoller
import org.snakeskin.dsl.Setup
import org.snakeskin.dsl.Subsystems
import org.snakeskin.registry.Controllers
import org.team401.brb2019.auto.EverybotAutoRoutine
import org.team401.brb2019.subsystems.DrivetrainSubsystem

@Setup
fun setup() {
    ControlPoller.pollInAutonomous = true

    AutoManager.setAutoLoop(EverybotAutoRoutine)

    //CameraServer.getInstance().startAutomaticCapture()

    Subsystems.add(DrivetrainSubsystem)
    Controllers.add(LeftStick, RightStick)

}
