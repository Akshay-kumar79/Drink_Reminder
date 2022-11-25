package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import javax.inject.Inject

class AddTrackableDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {

    suspend operator fun invoke(trackableDrink: TrackableDrink) {
        waterRepository.insertTrackableDrink(trackableDrink)
    }

}