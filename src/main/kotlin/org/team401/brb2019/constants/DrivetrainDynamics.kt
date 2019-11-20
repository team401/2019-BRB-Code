package org.team401.brb2019.constants

import org.team401.taxis.template.DriveDynamicsTemplate

object DrivetrainDynamics: DriveDynamicsTemplate {
    override val angularDrag  = 1.0
    override val inertialMass  = 27.10
    override val leftKa = 0.0405
    override val leftKs = 1.0218
    override val leftKv = 0.2046
    override val momentOfInertia = .1
    override val rightKa = 0.0302
    override val rightKs = 1.0036
    override val rightKv = 0.2103
    override val trackScrubFactor = 1.0849089799701466
}