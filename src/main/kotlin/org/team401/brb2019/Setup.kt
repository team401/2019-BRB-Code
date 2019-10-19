package org.team401.brb2019

import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Compressor
import org.snakeskin.auto.AutoManager
import org.snakeskin.controls.ControlPoller
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.snakeskin.registry.Controllers
import org.team401.brb2019.auto.EverybotAutoRoutine
import org.team401.brb2019.subsystems.HatchSubsystem
import org.team401.brb2019.subsystems.CargoSubsystem
import org.team401.brb2019.subsystems.ClimbingSubsystem
import org.team401.brb2019.subsystems.DrivetrainSubsystem

@Setup
fun setup() {
    ControlPoller.pollInAutonomous = true

    AutoManager.setAutoLoop(EverybotAutoRoutine)

    CameraServer.getInstance().startAutomaticCapture()

    Subsystems.add(DrivetrainSubsystem, CargoSubsystem, HatchSubsystem, ClimbingSubsystem)
    Controllers.add(LeftStick, RightStick, Gamepad)

    on (Events.DISABLED) {
        LEDManager.setDisabled()
    }

    on (Events.ENABLED) {
        LEDManager.update(isScoring = false, isIntaking = false, active = SuperstructureRoutines.activeGamepieceMode)
    }
}

fun auto() {
    on (Events.AUTO_ENABLED) {
    }
}