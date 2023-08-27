package com.akshaw.drinkreminder.waterdomain.repository

import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import java.time.LocalDateTime
import java.time.LocalTime

interface ReminderScheduler {
    
    /**
     *  Schedule the reminder with most recent upcoming alarm UNIX time in regards with [DrinkReminder.activeDays]
     *
     *  @return [LocalDateTime] for which reminder is scheduled
     */
    fun schedule(drinkReminder: DrinkReminder): LocalDateTime?
    fun cancel(drinkReminder: DrinkReminder)
    
}