package org.team401.brb2019.auto.steps

import org.snakeskin.auto.steps.AutoStep
import org.snakeskin.auto.steps.SingleStep
import org.team401.brb2019.subsystems.CargoSubsystem
import org.team401.brb2019.subsystems.HatchSubsystem

class SubsystemHomeStep: SingleStep() {
    override fun entry(currentTime: Double) {
        CargoSubsystem.cargoMachine.setState(CargoSubsystem.CargoStates.Stowed)
        HatchSubsystem.hatchMachine.setState(HatchSubsystem.States.Stowed)
    }
}