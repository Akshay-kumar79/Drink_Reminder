package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import java.time.LocalDateTime
import javax.inject.Inject

/**
 *  Schedule Next Alarm for the [DrinkReminder]
 *
 *  @return
 *  [Result.success] with local date time,
 *  [Result.failure] if scheduler failed to schedule alarm
 */
class ScheduleDrinkReminder @Inject constructor(
    private val reminderScheduler: ReminderScheduler
) {
    
    operator fun invoke(drinkReminder: DrinkReminder): Result<LocalDateTime> {
        return reminderScheduler.schedule(drinkReminder)
    }
    
}