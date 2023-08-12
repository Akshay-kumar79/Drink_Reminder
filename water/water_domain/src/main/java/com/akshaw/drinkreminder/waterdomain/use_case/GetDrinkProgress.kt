package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.waterdomain.model.Drink
import com.akshaw.drinkreminder.waterdomain.model.convertUnit
import javax.inject.Inject
import kotlin.math.ceil

/**
 *  Provides sum of drink amount after converting given drinks to current waterUnit type
 */
class GetDrinkProgress @Inject constructor(
    private val preferences: Preferences
) {

    operator fun invoke(drinks: List<com.akshaw.drinkreminder.waterdomain.model.Drink>): Double {
        val waterUnit = preferences.loadWaterUnit()

        return drinks.sumOf {
            it.convertUnit(waterUnit).waterIntake
        }
    }

}