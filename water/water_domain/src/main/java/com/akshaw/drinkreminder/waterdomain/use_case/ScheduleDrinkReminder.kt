package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderScheduler
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