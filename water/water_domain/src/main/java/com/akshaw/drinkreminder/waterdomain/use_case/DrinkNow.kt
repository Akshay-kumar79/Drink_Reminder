package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import javax.inject.Inject

/**
 * -> returns failure, if trackable drink id is -1
 * -> returns success with id, if trackable drink is genuine
 */
class DrinkNow @Inject constructor(
    private val preferences: Preferences,
    private val addDrink: AddDrink
) {
    
    suspend operator fun invoke(trackableDrink: TrackableDrink): Result<Long> {
        if (trackableDrink.id == -1L)
            return Result.failure(Exception("Add some drink quantity"))
        
        if (trackableDrink.amount <= 0)
            return Result.failure(Exception("Invalid quantity"))
        
        addDrink(
            Drink(
                waterIntake = trackableDrink.amount,
                unit = trackableDrink.unit
            )
        ).let {
            preferences.saveSelectedTrackableDrinkId(trackableDrink.id ?: -1)
            return Result.success(it)
        }
        
    }
    
}