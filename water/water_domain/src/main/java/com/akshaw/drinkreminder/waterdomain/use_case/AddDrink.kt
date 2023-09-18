package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import javax.inject.Inject

/**
 *  Inserts Drink to database
 *
 *  @return
 *  -> Success with drink id
 */
class AddDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {
    
    suspend operator fun invoke(drink: Drink): Long {
        
        return waterRepository.insertDrink(drink)
    }
    
}