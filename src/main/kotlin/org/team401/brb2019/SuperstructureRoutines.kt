package org.team401.brb2019

import org.team401.brb2019.subsystems.CargoSubsystem
import org.team401.brb2019.subsystems.HatchSubsystem

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
        LEDManager.update(isScoring = false, isIntaking = false, active = activeGamepieceMode)
        stopScoring()
    }

    @Synchronized fun toggleGamepieceMode() {
        val toggledMode = when (activeGamepieceMode) {
            GamepieceMode.Hatch -> GamepieceMode.Cargo
            GamepieceMode.Cargo -> GamepieceMode.Hatch
        }
        updateGamepieceMode(toggledMode)
    }

    @Synchronized fun deployHatchPusher(){
        if(activeGamepieceMode == SuperstructureRoutines.GamepieceMode.Hatch){
            HatchSubsystem.pusherMachine.setState(HatchSubsystem.PusherStates.Deployed)
        }
    }

    @Synchronized fun retractHatchPusher(){
        HatchSubsystem.pusherMachine.setState(HatchSubsystem.PusherStates.Stowed)
    }

    @Synchronized fun startIntaking() {
        when (activeGamepieceMode) {
            GamepieceMode.Hatch -> {
                HatchSubsystem.hatchMachine.setState(HatchSubsystem.States.Intaking)
            }

            GamepieceMode.Cargo -> {
                CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Intaking)
            }
        }
        LEDManager.update(isScoring = false, isIntaking = true, active = activeGamepieceMode)
    }

    @Synchronized fun stopIntaking() {
        when (activeGamepieceMode) {
            GamepieceMode.Hatch ->{
                HatchSubsystem.hatchMachine.setState(HatchSubsystem.States.Holding)
            }
            GamepieceMode.Cargo ->{
                CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Shuttling)
            }
        }
        LEDManager.update(isScoring = false, isIntaking = false, active = activeGamepieceMode)
    }

    @Synchronized fun startScoring() {
        when (activeGamepieceMode) {
            GamepieceMode.Hatch -> {
                HatchSubsystem.hatchMachine.setState(HatchSubsystem.States.Scoring)
            }

            GamepieceMode.Cargo -> {
                CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Scoring)

            }
        }
        LEDManager.update(isScoring = true, isIntaking = false, active = activeGamepieceMode)
    }

    @Synchronized fun stopScoring() {
        HatchSubsystem.hatchMachine.setState(HatchSubsystem.States.Stowed)
        CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Stowed)
        LEDManager.update(isScoring = false, isIntaking = false, active = activeGamepieceMode)
    }
}