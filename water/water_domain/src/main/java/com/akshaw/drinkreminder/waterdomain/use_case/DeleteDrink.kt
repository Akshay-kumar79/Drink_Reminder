package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import javax.inject.Inject

/**
 *  Delete Drink from database
 */
class DeleteDrink @Inject constructor(
    private val waterRepository: WaterRepository
) {

    suspend operator fun invoke(drink: com.akshaw.drinkreminder.waterdomain.model.Drink){
        waterRepository.removeDrink(drink)
    }

}