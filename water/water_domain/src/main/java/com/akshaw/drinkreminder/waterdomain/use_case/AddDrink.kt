package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import javax.inject.Inject

/**
 *  Inserts Drink to database
 *
 *  returns
 *  -> Success with drink id, if waterUnit is not Invalid
 *  -> Failure with exception, if waterUnit is Invalid
 */
class AddDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {
    
    suspend operator fun invoke(drink: Drink): Result<Long> {
        
        if (drink.unit == WaterUnit.Invalid)
            return Result.failure(Exception("Invalid water unit"))
        
        val id = waterRepository.insertDrink(drink)
        
        return Result.success(id)
    }
    
}