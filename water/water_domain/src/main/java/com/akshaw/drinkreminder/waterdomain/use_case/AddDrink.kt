package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import javax.inject.Inject

/**
 * Inserts Drink to database
 */
class AddDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {
    
    /**
     *  @return
     *  - [Result.failure] if [drink]'s waterIntake <= 0
     *  - [Result.success] with drink id
     */
    suspend operator fun invoke(drink: Drink): Result<Long> {
        
        if (drink.waterIntake <= 0) {
            return Result.failure(Exception("Drink quantity isn't valid"))
        }
        
        return Result.success(waterRepository.insertDrink(drink))
    }
    
}