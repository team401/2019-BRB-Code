package org.team401.brb2019.constants

import org.snakeskin.measure.Inches
import org.snakeskin.measure.distance.linear.LinearDistanceMeasureInches
import org.snakeskin.template.TankDrivetrainGeometryTemplate

object DrivetrainGeometry : TankDrivetrainGeometryTemplate {
    override val wheelRadius = 3.0.Inches
    override val wheelbase = 22.75.Inches
}