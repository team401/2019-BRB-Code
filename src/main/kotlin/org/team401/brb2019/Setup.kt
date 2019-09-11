package org.team401.brb2019

import org.snakeskin.dsl.*
import org.snakeskin.registry.Controllers
import org.team401.brb2019.subsystems.HatchSubsystem
import org.team401.brb2019.subsystems.CargoSubsystem

@Setup
fun setup() {
    Subsystems.add(CargoSubsystem, HatchSubsystem)
    Controllers.add(LeftStick, RightStick, Gamepad)
}
