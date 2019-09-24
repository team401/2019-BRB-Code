package org.team401.brb2019

import org.snakeskin.controls.ControlPoller
import org.snakeskin.dsl.*
import org.snakeskin.registry.Controllers
import org.team401.brb2019.subsystems.HatchSubsystem
import org.team401.brb2019.subsystems.CargoSubsystem
import org.team401.brb2019.subsystems.DrivetrainSubsystem

@Setup
fun setup() {
    ControlPoller.pollInAutonomous = true

    Subsystems.add(DrivetrainSubsystem, CargoSubsystem, HatchSubsystem)
    Controllers.add(LeftStick, RightStick, Gamepad)
}