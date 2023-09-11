package com.akshaw.drinkreminder.core.domain.repository

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import java.time.LocalDateTime

interface ReminderScheduler {
    
    /**
     *  Schedule the reminder with most recent upcoming alarm UNIX time in regards with [DrinkReminder.activeDays]
     *
     *  @return [LocalDateTime] for which reminder is scheduled
     */
    fun schedule(drinkReminder: DrinkReminder): Result<LocalDateTime>
    fun cancel(drinkReminder: DrinkReminder)
    
}