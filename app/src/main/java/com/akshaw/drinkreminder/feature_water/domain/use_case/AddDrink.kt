package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import javax.inject.Inject

/**
 * -> returns failure, if trackable drink id is -1
 * -> returns success with id, it trackable drink is genuine
 */
class AddDrink @Inject constructor(
    private val waterRepository: WaterRepository,
    private val preferences: Preferences
) {

    suspend operator fun invoke(trackableDrink: TrackableDrink): Result<Long>{
        if (trackableDrink.id == -1L)
            return Result.failure(Exception("Add some drinks"))

        val id = waterRepository.insertDrink(
            Drink(
                waterIntake = trackableDrink.amount,
                unit = trackableDrink.unit
            )
        )
        preferences.saveSelectedTrackableDrinkId(trackableDrink.id ?: -1)

        return Result.success(id)
    }

}