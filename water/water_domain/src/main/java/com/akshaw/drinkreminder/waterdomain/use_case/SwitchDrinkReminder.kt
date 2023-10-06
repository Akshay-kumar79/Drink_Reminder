package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import javax.inject.Inject

/**
 *  Use case to turn on or off a [DrinkReminder] saved in local db
 */
class SwitchDrinkReminder @Inject constructor(
    private val upsertDrinkReminder: UpsertDrinkReminder,
    private val scheduleDrinkReminder: ScheduleDrinkReminder,
    private val cancelDrinkReminder: CancelDrinkReminder
) {
    
    /**
     *  This use case reschedule reminder when turned on
     *
     *  @param drinkReminder [DrinkReminder] in local db that needs to be updated and rescheduled
     *  @param isReminderOn [Boolean] value indicating whether to turn on or off
     *
     *  @return
     *  -> [Result.success] when switch successful
     *
     *  -> [Result.failure] when failed to schedule reminder in case user doesn't have required permission.
     *  Exception should be either [NoExactAlarmPermissionException] or [NoNotificationPermissionException]
     */
    suspend operator fun invoke(drinkReminder: DrinkReminder, isReminderOn: Boolean): Result<Unit> {
        
        if (isReminderOn) {
            scheduleDrinkReminder(drinkReminder.copy(isReminderOn = true))
                .onSuccess {
                    upsertDrinkReminder(drinkReminder.copy(isReminderOn = true))
                }
                .onFailure {
                    if (it is NoExactAlarmPermissionException || it is NoNotificationPermissionException) {
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