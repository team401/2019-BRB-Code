package org.team401.brb2019

import org.lightlink.LightLink

object LEDManager {
    private val ll = LightLink()

    @Synchronized
    fun setDisabled() {
        ll.rainbow(LightLink.Speed.SLOW, 0)
        ll.rainbow(LightLink.Speed.SLOW, 1)
    }

    @Synchronized
    fun setHatchTool() {
        ll.solid(LightLink.Color.YELLOW, 0)
        ll.solid(LightLink.Color.YELLOW, 1)
    }

    @Synchronized
    fun setCargoTool() {
        ll.solid(LightLink.Color.GREEN, 0)
        ll.solid(LightLink.Color.GREEN, 1)
    }

    @Synchronized
    fun update(isScoring: Boolean, isIntaking: Boolean, active: SuperstructureRoutines.GamepieceMode) {
        if (isScoring) {
            ll.solid(LightLink.Color.RED, 0)
            ll.solid(LightLink.Color.RED, 1)
            return
        }
        if (isIntaking) {
            ll.solid(LightLink.Color.BLUE, 0)
            ll.solid(LightLink.Color.BLUE, 1)
            return
        }
        when (active) {
            SuperstructureRoutines.GamepieceMode.Hatch -> setHatchTool()
            SuperstructureRoutines.GamepieceMode.Cargo -> setCargoTool()
        }
    }

    @Synchronized
    fun signalIntake() {
        ll.signal(LightLink.Color.BLUE, 0)
        ll.signal(LightLink.Color.BLUE, 1)
    }
}