package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import javax.inject.Inject

/**
 *  Schedule Next Alarm for the [DrinkReminder]
 */
class ScheduleDrinkReminder @Inject constructor(
    private val reminderScheduler: ReminderScheduler
) {
    
    operator fun invoke(drinkReminder: DrinkReminder){
        reminderScheduler.schedule(drinkReminder)
    }
    
}