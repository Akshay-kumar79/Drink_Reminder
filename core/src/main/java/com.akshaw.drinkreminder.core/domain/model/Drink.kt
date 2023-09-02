package com.akshaw.drinkreminder.core.domain.model

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import java.time.LocalDateTime

data class Drink(
    var id: Long? = null,
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var waterIntake: Double,
    var unit: WaterUnit
)


fun Drink.convertUnit(newUnit: WaterUnit): Drink {
    return when (newUnit) {
        WaterUnit.ML -> {
            when (unit) {
                WaterUnit.ML -> this.copy()
                WaterUnit.FL_OZ -> {
                    this.copy(
                        waterIntake = waterIntake * Constants.FLOZ_TO_ML,
                        unit = WaterUnit.ML
                    )
                }
                WaterUnit.Invalid -> {
                    this.copy(
                        waterIntake = 0.0,
                        unit = WaterUnit.ML
                    )
                }
            }
        }
        WaterUnit.FL_OZ -> {
            when (unit) {
                WaterUnit.ML -> {
                    this.copy(
                        waterIntake = waterIntake * Constants.ML_TO_FLOZ,
                        unit = WaterUnit.FL_OZ
                    )
                }
                WaterUnit.FL_OZ -> this.copy()
                WaterUnit.Invalid -> {
                    this.copy(
                        waterIntake = 0.0,
                        unit = WaterUnit.FL_OZ
                    )
                }
            }
        }
        WaterUnit.Invalid -> {
            this.copy(
                waterIntake = 0.0,
                unit = WaterUnit.Invalid
            )
        }
    }
}