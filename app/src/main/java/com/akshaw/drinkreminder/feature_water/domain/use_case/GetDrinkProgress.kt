package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.convertUnit
import javax.inject.Inject

class GetDrinkProgress @Inject constructor(
    private val preferences: Preferences
) {

    operator fun invoke(drinks: List<Drink>): Double {
        val waterUnit = preferences.loadWaterUnit()

        return drinks.sumOf {
            it.convertUnit(waterUnit).waterIntake
        }
    }

}