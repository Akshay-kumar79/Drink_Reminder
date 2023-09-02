package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class SaveAndScheduleInitialReminders @Inject constructor(
    private val preferences: Preferences,
    private val upsertDrinkReminder: UpsertDrinkReminder,
    private val scheduleDrinkReminder: ScheduleDrinkReminder
) {
    
    suspend operator fun invoke() {
        
        if (preferences.getIsOnboardingCompleted().first()) {
            return
        }
        
        var start = preferences.getWakeupTime().first()
        val end = preferences.getBedTime().first()
    
        while (start.plusMinutes(90).isBefore(end)) {
            start = start.plusMinutes(90)
        }
        
        val startOffsetMinute = Duration.between(start, end).toMinutes() / 2
        
        start = preferences.getWakeupTime().first()
        var startOffsetTime = start.plusMinutes(startOffsetMinute)
        val list = mutableListOf<LocalTime>(startOffsetTime)
        while (start.plusMinutes(90).isBefore(end)) {
            start = start.plusMinutes(90)
            startOffsetTime = startOffsetTime.plusMinutes(90)
            list.add(startOffsetTime)
        }
        
        list.forEach {
            val drinkReminder = DrinkReminder(
                time = it,
                isReminderOn = true,
                activeDays = DayOfWeek.values().toList()
            )
            val id = upsertDrinkReminder(drinkReminder)
            scheduleDrinkReminder(drinkReminder.copy(id = id))
        }
    }
    
}