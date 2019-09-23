package org.team401.brb2019

import org.snakeskin.dsl.*

val LeftStick = HumanControls.attack3(0) {
    invertAxis(Axes.PITCH)
}

val RightStick = HumanControls.attack3(1) {
}

val Gamepad = HumanControls.f310(3) {
    whenButton(Buttons.LEFT_BUMPER) {
        pressed {
            SuperstructureRoutines.toggleGamepieceMode()
        }
    }

    whenButton(Buttons.B) {
        pressed {
            SuperstructureRoutines.startIntaking()
        }
        released { 
            SuperstructureRoutines.stopIntaking()
        }
    }

    whenButton(Buttons.Y) {
        pressed {
            SuperstructureRoutines.startScoring()
        }
        released {
            SuperstructureRoutines.stopScoring()
        }
    }
}