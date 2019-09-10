package org.team401.brb2019

import org.snakeskin.dsl.*
import org.team401.brb2019.subsystems.CargoSubsystem

@Setup
fun setup() {
    Subsystems.add(CargoSubsystem)
}
