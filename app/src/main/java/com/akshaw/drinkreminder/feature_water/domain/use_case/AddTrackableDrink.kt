package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
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
        
        if (trackableDrink.unit == WaterUnit.INVALID)
            return Result.failure(Exception("Invalid water unit"))
        
        val id = waterRepository.insertTrackableDrink(trackableDrink)
        
        return Result.success(id)
    }

}