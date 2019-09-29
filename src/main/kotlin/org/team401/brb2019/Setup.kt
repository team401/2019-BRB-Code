package org.team401.brb2019

import edu.wpi.first.wpilibj.Compressor
import org.snakeskin.controls.ControlPoller
import org.snakeskin.dsl.*
import org.snakeskin.registry.Controllers
import org.team401.brb2019.subsystems.HatchSubsystem
import org.team401.brb2019.subsystems.CargoSubsystem
import org.team401.brb2019.subsystems.ClimbingSubsystem
import org.team401.brb2019.subsystems.DrivetrainSubsystem

@Setup
fun setup() {
    ControlPoller.pollInAutonomous = true

    val compressor = Compressor()
    compressor.stop()

    Subsystems.add(DrivetrainSubsystem, CargoSubsystem, HatchSubsystem, ClimbingSubsystem)
    Controllers.add(LeftStick, RightStick, Gamepad)
}