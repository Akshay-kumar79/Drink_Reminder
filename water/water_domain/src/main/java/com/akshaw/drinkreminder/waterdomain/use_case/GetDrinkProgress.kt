package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.convertUnit

/**
 *  Provides sum of drink amount after converting given drinks to current waterUnit type
 */
class GetDrinkProgress  {

    operator fun invoke(drinks: List<Drink>, currentWaterUnit: WaterUnit): Double {

        return drinks.sumOf {
            it.convertUnit(currentWaterUnit).waterIntake
        }
    }

}