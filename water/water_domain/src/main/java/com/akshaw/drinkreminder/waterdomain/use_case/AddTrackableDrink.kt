package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import javax.inject.Inject

/**
 *  Inserts TrackableDrink to database
 *
 *  returns
 *  -> Success with trackableDrink id, if waterUnit is not Invalid
 *  -> Failure with Exception, if waterUnit is Invalid
 */
class AddTrackableDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {

    suspend operator fun invoke(trackableDrink: TrackableDrink): Result<Long> {
        
        if (trackableDrink.unit == WaterUnit.Invalid)
            return Result.failure(Exception("Invalid water unit"))
        
        val id = waterRepository.insertTrackableDrink(trackableDrink)
        
        return Result.success(id)
    }

}