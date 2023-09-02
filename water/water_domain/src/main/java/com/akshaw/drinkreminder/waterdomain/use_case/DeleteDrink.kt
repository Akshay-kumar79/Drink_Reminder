package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import javax.inject.Inject

/**
 *  Delete Drink from database
 */
class DeleteDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {

    suspend operator fun invoke(drink: Drink){
        waterRepository.removeDrink(drink)
    }

}