package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink
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
            return Result.failure(Exception("Add some drinks"))
        
        if (trackableDrink.amount <= 0)
            return Result.failure(Exception("Drink valid quantity"))
        
        if (trackableDrink.unit == WaterUnit.INVALID)
            return Result.failure(Exception("Invalid water unit"))
        
        addDrink(
            com.akshaw.drinkreminder.waterdomain.model.Drink(
                waterIntake = trackableDrink.amount,
                unit = trackableDrink.unit
            )
        ).onSuccess {
            preferences.saveSelectedTrackableDrinkId(trackableDrink.id ?: -1)
            return Result.success(it)
        }.onFailure {
            return Result.failure(Exception(it.message ?: "Something went wrong"))
        }
        
        return Result.failure(Exception("Something went wrong"))
    }
    
}