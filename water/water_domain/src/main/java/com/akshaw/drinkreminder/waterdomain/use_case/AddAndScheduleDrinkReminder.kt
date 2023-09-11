package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.data.repository.ReminderSchedulerImpl
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
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
     *  @param selectedDays a map containing [DayOfWeek.ordinal] as key and boolean
     *  for respective [DayOfWeek] indicating if the day is selected or not
     */
    suspend operator fun invoke(hour: Int, minute: Int, selectedDays: Map<Int, Boolean>) {
        val drinkReminder = DrinkReminder(
            time = LocalTime.now().withHour(hour).withMinute(minute),
            isReminderOn = true,
            activeDays = DayOfWeek.values().filter {
                selectedDays[it.value] == true
            }
        )
        
        val id = upsertDrinkReminder(drinkReminder)
        scheduleDrinkReminder(drinkReminder.copy(id = id))
            .onFailure {
                if (it is ReminderSchedulerImpl.NoExactAlarmPermissionException || it is ReminderSchedulerImpl.NoNotificationPermissionException) {
                    upsertDrinkReminder(drinkReminder.copy(id = id, isReminderOn = false))
                }
            }
       
    }
    
}