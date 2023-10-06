package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

/**
 *  This Use-case add new drink reminder to local db and schedule the reminder for same
 *  if user doesn't hold required permission then reminder will be saved as off state
 */
class AddAndScheduleDrinkReminder @Inject constructor(
    private val upsertDrinkReminder: UpsertDrinkReminder,
    private val scheduleDrinkReminder: ScheduleDrinkReminder
) {
    
    /**
     *  @param hour hour of day for which reminder will be scheduled
     *  @param minute minute for which reminder will be scheduled
     *  @param selectedDays a map containing [DayOfWeek.getValue] as key and a boolean
     *  value for respective [DayOfWeek] indicating if the day is selected or not
     *
     *  @return
     *  -> [Result.success] if successfully scheduled and added [DrinkReminder] irrespective of
     *  user have all permission or not.
     *
     *  -> [Result.failure] if failed parse [LocalTime] from [hour] and [minute]
     */
    suspend operator fun invoke(hour: Int, minute: Int, selectedDays: Map<Int, Boolean>): Result<Unit> {
        
        val localTime = try {
            LocalTime.of(hour, minute)
        } catch (e: Exception) {
            return Result.failure(e)
        }
        
        val drinkReminder = DrinkReminder(
            time = localTime,
            isReminderOn = true,
            activeDays = DayOfWeek.values().filter {
                selectedDays[it.value] == true
            }
        )
        
        val id = upsertDrinkReminder(drinkReminder)
        scheduleDrinkReminder(drinkReminder.copy(id = id))
            .onFailure {
                if (it is NoExactAlarmPermissionException || it is NoNotificationPermissionException) {
                    upsertDrinkReminder(drinkReminder.copy(id = id, isReminderOn = false))
                }
            }
        
        return Result.success(Unit)
    }
    
}