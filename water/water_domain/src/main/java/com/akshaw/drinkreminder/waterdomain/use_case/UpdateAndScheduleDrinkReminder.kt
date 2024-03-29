package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

/**
 *  This Use-case update drink reminder to local db and schedule the reminder for same if the reminder is on
 *  if user doesn't hold required permission then reminder will be turned off
 */
class UpdateAndScheduleDrinkReminder @Inject constructor(
    private val scheduleDrinkReminder: ScheduleDrinkReminder,
    private val upsertDrinkReminder: UpsertDrinkReminder,
) {
    
    /**
     *  @param drinkReminder the [DrinkReminder] that need to be updated with new provided values
     *  @param newHour hour of day for which reminder will be scheduled
     *  @param newMinute minute for which reminder will be scheduled
     *  @param newSelectedDays a map containing [DayOfWeek.ordinal] as key and boolean
     *  for respective [DayOfWeek] indicating if the day is selected or not
     *
     *  @return
     *  -> [Result.success] if successfully rescheduled and updated [DrinkReminder] irrespective of
     *  user have all permission or not.
     *
     *  -> [Result.failure] if failed parse [LocalTime] from [newHour] and [newMinute]
     */
    suspend operator fun invoke(drinkReminder: DrinkReminder, newHour: Int, newMinute: Int, newSelectedDays: Map<Int, Boolean>): Result<Unit> {
    
        val localTime = try {
            LocalTime.of(newHour, newMinute)
        } catch (e: Exception) {
            return Result.failure(e)
        }
        
        val updateDrinkReminder = DrinkReminder(
            id = drinkReminder.id,
            time = localTime,
            isReminderOn = drinkReminder.isReminderOn,
            activeDays = DayOfWeek.values().filter {
                newSelectedDays[it.value] == true
            }
        )
        
        withContext(Dispatchers.IO) {
            if (drinkReminder.isReminderOn)
                scheduleDrinkReminder(updateDrinkReminder)
                    .onSuccess {
                        upsertDrinkReminder(updateDrinkReminder)
                    }
                    .onFailure {
                        if (it is NoExactAlarmPermissionException || it is NoNotificationPermissionException)
                            upsertDrinkReminder(updateDrinkReminder.copy(isReminderOn = false))
                        else
                            upsertDrinkReminder(updateDrinkReminder)
                    }
            else {
                upsertDrinkReminder(updateDrinkReminder)
            }
        }
    
        return Result.success(Unit)
    }
    
}