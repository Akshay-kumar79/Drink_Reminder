package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTrackableDrinks @Inject constructor(
    private val repository: WaterRepository
) {
    
    operator fun invoke(): Flow<List<TrackableDrink>> {
        return repository.getAllTrackableDrinks()
    }
    
}