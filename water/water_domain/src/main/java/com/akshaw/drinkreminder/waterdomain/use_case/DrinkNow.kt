package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.AddSomeDrinksException
import com.akshaw.drinkreminder.core.util.InvalidDrinkQuantityException
import com.akshaw.drinkreminder.core.util.SWWException
import javax.inject.Inject

/**
 *  Add Drink to local database with current date and time
 */
class DrinkNow @Inject constructor(
    private val preferences: Preferences,
    private val addDrink: AddDrink
) {
    
    /**
     * @param trackableDrink [TrackableDrink] that need to be tracked.
     *  if [trackableDrink] id is -1, then it will be considered that there is not trackable
     *  drink available to track.
     *
     * @return
     * -> [Result.failure],
     * - with [AddSomeDrinksException] if [trackableDrink] id is -1
     * - with [InvalidDrinkQuantityException] if [trackableDrink] amount <= 0
     * - if [AddDrink] returns [Result.failure]
     * - with [SWWException] if program reached the end of function which should not happen
     *
     * -> [Result.success] with id, if trackable drink is genuine
     */
    suspend operator fun invoke(trackableDrink: TrackableDrink): Result<Long> {
        if (trackableDrink.id == -1L)
            return Result.failure(AddSomeDrinksException())
        
        if (trackableDrink.amount <= 0)
            return Result.failure(InvalidDrinkQuantityException())
        
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
        
        return Result.failure(SWWException())
    }
}