package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import javax.inject.Inject

/**
 *  Delete TrackableDrink from database
 */
class DeleteTrackableDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {

    suspend operator fun invoke(trackableDrink: TrackableDrink) {
        waterRepository.removeTrackableDrink(trackableDrink)
    }

}