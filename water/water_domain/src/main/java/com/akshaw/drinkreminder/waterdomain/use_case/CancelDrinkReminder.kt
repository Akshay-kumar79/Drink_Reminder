package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderScheduler
import javax.inject.Inject

/**
 *  Cancel the reminder for [DrinkReminder]
 */
class CancelDrinkReminder @Inject constructor(
    private val reminderScheduler: ReminderScheduler
) {
    operator fun invoke(drinkReminder: DrinkReminder) {
        reminderScheduler.cancel(drinkReminder)
    }
    
}