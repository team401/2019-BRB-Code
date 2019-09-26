package org.team401.brb2019

import org.team401.brb2019.subsystems.CargoSubsystem

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
                CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Intaking)
            }
        }
    }

    @Synchronized fun stopIntaking() {
        //Insert Hatch code here
        CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Shuttling)
    }

    @Synchronized fun startScoring() {
        when (activeGamepieceMode) {
            GamepieceMode.Hatch -> {

            }

            GamepieceMode.Cargo -> {
                CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Scoring)

            }
        }
    }

    @Synchronized fun stopScoring() {
        //Insert Hatch code here
        CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Stowed)
    }
}