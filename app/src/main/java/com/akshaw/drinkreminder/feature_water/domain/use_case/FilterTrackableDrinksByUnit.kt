package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

/**
 *  Provides list of drink of given waterUnit type after filtering from allTrackableDrinks
 */
class FilterTrackableDrinksByUnit {
    
    operator fun invoke(waterUnit: WaterUnit, allTrackableDrink: List<TrackableDrink>): List<TrackableDrink> {
        
        return allTrackableDrink.filter { trackableDrink ->
            trackableDrink.unit == waterUnit
        }
    }
    
}