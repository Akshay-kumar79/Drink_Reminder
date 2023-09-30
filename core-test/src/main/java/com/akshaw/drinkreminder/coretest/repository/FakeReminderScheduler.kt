package com.akshaw.drinkreminder.coretest.repository

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import java.time.LocalDateTime

class FakeReminderScheduler : ReminderScheduler {
    
    var reminders = mutableListOf<DrinkReminder>()
    var scheduleException: Exception? = null
    
    override fun schedule(drinkReminder: DrinkReminder): Result<LocalDateTime> {
        
        scheduleException?.let {
            return Result.failure(it)
        }
        
        reminders.removeIf { it.id == drinkReminder.id }
        reminders.add(drinkReminder)
        
        return Result.success(LocalDateTime.now())
    }
    
    override fun cancel(drinkReminder: DrinkReminder) {
        reminders.removeIf {
            it.id == drinkReminder.id
        }
    }
}