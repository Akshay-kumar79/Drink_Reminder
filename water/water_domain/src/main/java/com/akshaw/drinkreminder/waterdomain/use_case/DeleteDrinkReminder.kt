package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderRepository
import javax.inject.Inject

/**
 *  Remove the [DrinkReminder] from database
 */
class DeleteDrinkReminder @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(drinkReminder: DrinkReminder){
        reminderRepository.removeDrinkReminder(drinkReminder)
    }
}