package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.domain.repository.ReminderRepository
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 *  Re-schedule all the alarm saved locally.
 *  if permission not allowed then turn off all the alarms in [reminderRepository]
 */
class RescheduleAllTSDrinkReminders @Inject constructor(
    private val scheduleDrinkReminder: ScheduleDrinkReminder,
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke() {
        reminderRepository.getAllDrinkReminders().first()
            .forEach { drinkReminder ->

                scheduleDrinkReminder(drinkReminder)
                    .onFailure {
                        withContext(Dispatchers.IO) {
                            if (it is NoExactAlarmPermissionException || it is NoNotificationPermissionException)
                                reminderRepository.upsertDrinkReminder(drinkReminder.copy(isReminderOn = false))
                        }
                    }
            }
        
    }
    
}