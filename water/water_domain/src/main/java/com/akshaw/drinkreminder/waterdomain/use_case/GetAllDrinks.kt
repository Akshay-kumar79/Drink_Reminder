package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  Get all drinks from local database
 */
class GetAllDrinks @Inject constructor(
    private val repository: WaterRepository
) {
    
    /**
     *  @return flow that emits list of [Drink] in database and there changes
     */
    operator fun invoke(): Flow<List<Drink>> {
        return repository.getAllDrink()
    }
    
}