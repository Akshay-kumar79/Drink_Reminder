package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *  Inserts TrackableDrink to database
 *
 *  returns
 *  -> Success with trackableDrink id, if waterUnit is not Invalid
 *
 *  -> Failure with Exception,
 *  - if [TrackableDrink] with same amount and unit already exist in database
 */
class AddTrackableDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {
    
    suspend operator fun invoke(trackableDrink: TrackableDrink): Result<Long> {
        
        waterRepository.getAllTrackableDrinks().first()
            .forEach {
                if (it.unit == trackableDrink.unit && it.amount == trackableDrink.amount)
                    return Result.failure(Exception("Drink with same quantity already exist"))
            }
        
        val id = waterRepository.insertTrackableDrink(trackableDrink)
        
        return Result.success(id)
    }
    
}