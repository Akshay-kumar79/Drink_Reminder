package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.data.repository.ReminderSchedulerImpl
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import javax.inject.Inject

class SwitchDrinkReminder @Inject constructor(
    private val upsertDrinkReminder: UpsertDrinkReminder,
    private val scheduleDrinkReminder: ScheduleDrinkReminder,
    private val cancelDrinkReminder: CancelDrinkReminder
) {
    
    suspend operator fun invoke(drinkReminder: DrinkReminder, isReminderOn: Boolean): Result<Unit> {
        
        if (isReminderOn) {
            scheduleDrinkReminder(drinkReminder.copy(isReminderOn = true))
                .onSuccess {
                    upsertDrinkReminder(drinkReminder.copy(isReminderOn = true))
                }
                .onFailure {
                    if (it is ReminderSchedulerImpl.NoExactAlarmPermissionException || it is ReminderSchedulerImpl.NoNotificationPermissionException) {
                        upsertDrinkReminder(drinkReminder.copy(isReminderOn = false))
                        return Result.failure(it)
                    }
                }
        } else {
            upsertDrinkReminder(drinkReminder.copy(isReminderOn = false))
            cancelDrinkReminder(drinkReminder)
        }
        return Result.success(Unit)
    }
}