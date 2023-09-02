package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
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