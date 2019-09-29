package org.team401.brb2019

import org.snakeskin.dsl.*
import org.team401.brb2019.subsystems.ClimbingSubsystem

val LeftStick = HumanControls.attack3(0) {
    invertAxis(Axes.PITCH)
}

val RightStick = HumanControls.attack3(1) {
    whenButton(Buttons.STICK_BOTTOM) {
        pressed {
            ClimbingSubsystem.backMachine.setState(ClimbingSubsystem.ClimbingStates.Extended)
        }
        released {
            ClimbingSubsystem.backMachine.setState(ClimbingSubsystem.ClimbingStates.Stowed)
        }
    }
    whenButton(Buttons.STICK_TOP) {
        pressed {
            ClimbingSubsystem.frontMachine.setState(ClimbingSubsystem.ClimbingStates.Extended)
        }
        released {
            ClimbingSubsystem.frontMachine.setState(ClimbingSubsystem.ClimbingStates.Stowed)
        }
    }
}

val Gamepad = HumanControls.f310(2) {
    whenButton(Buttons.LEFT_BUMPER) {
        pressed {
            SuperstructureRoutines.toggleGamepieceMode()
        }
    }

    whenButton(Buttons.RIGHT_BUMPER){
        pressed{
            SuperstructureRoutines.deployHatchPusher()
        }
        released{
            SuperstructureRoutines.retractHatchPusher()
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