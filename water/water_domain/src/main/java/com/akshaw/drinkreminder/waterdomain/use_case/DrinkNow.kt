package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import javax.inject.Inject

/**
 *  Add Drink to local database with current date and time
 */
class DrinkNow @Inject constructor(
    private val preferences: Preferences,
    private val addDrink: AddDrink
) {
    
    /**
     * @return failure,
     * - if [trackableDrink] id is -1
     * - if [trackableDrink] amount <= 0
     * - if [AddDrink] returns [Result.failure]
     * - if program reached the end of function which should not happen
     *
     *  success with id, if trackable drink is genuine
     */
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
        ).onSuccess {
            preferences.saveSelectedTrackableDrinkId(trackableDrink.id ?: -1)
            return Result.success(it)
        }.onFailure {
            return Result.failure(it)
        }
        
        return Result.failure(Exception("Something went wrong"))
    }
}