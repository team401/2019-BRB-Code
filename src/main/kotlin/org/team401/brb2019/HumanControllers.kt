package org.team401.brb2019

import org.snakeskin.dsl.*

val LeftStick = HumanControls.attack3(0) {
    invertAxis(Axes.PITCH)
}

val RightStick = HumanControls.attack3(1)
