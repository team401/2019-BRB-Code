package org.team401.brb2019

import org.snakeskin.dsl.*
import org.snakeskin.logic.Direction
import org.team401.brb2019.subsystems.CargoSubsystem

val LeftStick = HumanControls.attack3(0) {
    invertAxis(Axes.PITCH)
}

val RightStick = HumanControls.attack3(1) {
}

val Gamepad = HumanControls.f310(3) {
}