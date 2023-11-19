package com.akshaw.drinkreminder.core.domain.repository

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import com.akshaw.drinkreminder.core.util.ReminderOffException
import java.time.LocalDateTime

/**
 *  Schedule and cancel drink reminders
 */
interface ReminderScheduler {
    
    /**
     *  Schedule the reminder with most recent upcoming alarm UNIX time in regards with [DrinkReminder.activeDays]
     *  if and only if [DrinkReminder.isReminderOn] is true. [DrinkReminder.id] will be used as an identifier to cancel
     *  the reminder
     *
     *  @return
     *
     *  -> [Result.failure]
     *  - with [NoNotificationPermissionException] if user don't have permission to show notification on SDK_VERSION >= 33
     *  - with [NoExactAlarmPermissionException] if user don't have permission to show notification on SDK_VERSION >= 31
     *  - with [ReminderOffException] if [DrinkReminder.isReminderOn] is false or [DrinkReminder.activeDays] is empty list
     *
     *  -> [Result.failure]
     *  - with [LocalDateTime] for which reminder is scheduled
     */
    fun schedule(drinkReminder: DrinkReminder): Result<LocalDateTime>
    
    /**
     *  Cancel the reminder scheduled corresponding to [DrinkReminder.id]
     */
    fun cancel(drinkReminder: DrinkReminder)
    
}