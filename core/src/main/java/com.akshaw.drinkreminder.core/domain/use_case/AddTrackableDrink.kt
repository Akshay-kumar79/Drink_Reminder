package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *  Inserts TrackableDrink to database
 *
 *  returns
 *  -> [Result.success] with trackableDrink id
 *
 *  -> [Result.failure] with Exception,
 *  - if [TrackableDrink.amount] <= 0
 *  - if [TrackableDrink] with same amount and unit already exist in database
 */
class AddTrackableDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {
    
    suspend operator fun invoke(trackableDrink: TrackableDrink): Result<Long> {
        
        if (trackableDrink.amount <= 0) {
            return Result.failure(Exception("Drink quantity isn't valid"))
        }
        
        waterRepository.getAllTrackableDrinks().first()
            .forEach {
                if (it.unit == trackableDrink.unit && it.amount == trackableDrink.amount)
                    return Result.failure(Exception("Drink with same quantity already exist"))
            }
        
        val id = waterRepository.insertTrackableDrink(trackableDrink)
        
        return Result.success(id)
    }
    
}