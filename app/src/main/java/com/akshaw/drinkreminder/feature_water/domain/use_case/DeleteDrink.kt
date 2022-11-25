package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import javax.inject.Inject

class DeleteDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {

    suspend operator fun invoke(drink: Drink){
        waterRepository.removeDrink(drink)
    }

}