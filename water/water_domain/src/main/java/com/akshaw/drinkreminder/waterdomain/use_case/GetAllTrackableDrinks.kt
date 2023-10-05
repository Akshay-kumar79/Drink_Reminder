package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  Get all [TrackableDrink] from local database
 */
class GetAllTrackableDrinks @Inject constructor(
    private val repository: WaterRepository
) {
    
    /**
     *  @return flow that emits list of [TrackableDrink] in database and there changes
     */
    operator fun invoke(): Flow<List<TrackableDrink>> {
        return repository.getAllTrackableDrinks()
    }
    
}