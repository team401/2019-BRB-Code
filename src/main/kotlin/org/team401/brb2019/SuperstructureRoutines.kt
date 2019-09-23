package org.team401.brb2019

object SuperstructureRoutines {
    enum class GamepieceMode {
        Hatch,
        Cargo
    }

    var activeGamepieceMode = GamepieceMode.Hatch
    @Synchronized get
    private set

    @Synchronized fun updateGamepieceMode(mode: GamepieceMode) {
        activeGamepieceMode = mode
    }

    @Synchronized fun toggleGamepieceMode() {
        val toggledMode = when (activeGamepieceMode) {
            GamepieceMode.Hatch -> GamepieceMode.Cargo
            GamepieceMode.Cargo -> GamepieceMode.Hatch
        }
        updateGamepieceMode(toggledMode)
    }

    @Synchronized fun startIntaking() {
        when (activeGamepieceMode) {
            GamepieceMode.Hatch -> {

            }

            GamepieceMode.Cargo -> {

            }
        }
    }

    @Synchronized fun stopIntaking() {

    }

    @Synchronized fun startScoring() {
        when (activeGamepieceMode) {
            GamepieceMode.Hatch -> {

            }

            GamepieceMode.Cargo -> {

            }
        }
    }

    @Synchronized fun stopScoring() {

    }
}