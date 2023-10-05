package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink

/**
 *  Filter Trackable drinks for a [WaterUnit]
 */
class FilterTrackableDrinksByUnit {
    
    
    /**
     * @param waterUnit [WaterUnit] of which drinks need to be filtered
     * @param allTrackableDrink list of [TrackableDrink] from which drinks will be filtered
     *
     *  @return list of drink of given waterUnit type after filtering from allTrackableDrinks
     */
    operator fun invoke(waterUnit: WaterUnit, allTrackableDrink: List<TrackableDrink>): List<TrackableDrink> {
        
        return allTrackableDrink.filter { trackableDrink ->
            trackableDrink.unit == waterUnit
        }
    }
    
}