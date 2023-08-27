package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderRepository
import javax.inject.Inject

/**
 *  Add [DrinkReminder] to database
 */
class UpsertDrinkReminder @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(drinkReminder: DrinkReminder): Long {
        return reminderRepository.upsertDrinkReminder(
            drinkReminder.copy(
                time = drinkReminder.time.withSecond(0).withNano(0)
            )
        )
    }
    
}