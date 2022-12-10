package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDrinks @Inject constructor(
    private val repository: WaterRepository
) {
    
    operator fun invoke(): Flow<List<Drink>> {
        return repository.getAllDrink()
    }
    
}