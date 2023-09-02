package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  return flow that emits all the trackable in database and there changes
 */
class GetAllTrackableDrinks @Inject constructor(
    private val repository: WaterRepository
) {
    
    operator fun invoke(): Flow<List<TrackableDrink>> {
        return repository.getAllTrackableDrinks()
    }
    
}