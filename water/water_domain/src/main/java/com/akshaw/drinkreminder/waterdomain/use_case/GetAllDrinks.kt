package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.model.Drink
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  return flow that emits all the drink in database and there changes
 */
class GetAllDrinks @Inject constructor(
    private val repository: WaterRepository
) {
    
    operator fun invoke(): Flow<List<Drink>> {
        return repository.getAllDrink()
    }
    
}