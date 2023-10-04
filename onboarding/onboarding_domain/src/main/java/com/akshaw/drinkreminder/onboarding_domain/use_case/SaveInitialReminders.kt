package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

/**
 *  Save Time specific reminders to local database when [Preferences.getIsOnboardingCompleted] is false
 *
 *  These reminders are calculated based on [Preferences.getBedTime] and [Preferences.getWakeupTime]
 *  in a way that all reminders are between wakeup time and bed time
 */
class SaveInitialReminders @Inject constructor(
    private val preferences: Preferences,
    private val upsertDrinkReminder: UpsertDrinkReminder,
) {
    
    suspend operator fun invoke() {
        
        if (preferences.getIsOnboardingCompleted().first()) {
            return
        }
        
        getRemindersTimes().forEach {
            val drinkReminder = DrinkReminder(
                time = it,
                isReminderOn = true,
                activeDays = DayOfWeek.values().toList()
            )
            upsertDrinkReminder(drinkReminder)
        }
    }
    
    private suspend fun getRemindersTimes(): List<LocalTime> {
        // Converting LocalTime to LocalDateTime, to generate reminders times in case when end time is before start time
        var start = LocalDateTime.of(LocalDate.now(), preferences.getWakeupTime().first())
        var end = LocalDateTime.of(LocalDate.now(), preferences.getBedTime().first())
        
        if (start.isAfter(end)) {
            end = end.plusDays(1)
        }
        
        while (start.plusMinutes(90).isBefore(end)) {
            start = start.plusMinutes(90)
        }
        
        val startOffsetMinute = Duration.between(start, end).toMinutes() / 2
        
        start = LocalDateTime.of(LocalDate.now(), preferences.getWakeupTime().first())
        var startOffsetTime = start.plusMinutes(startOffsetMinute)
        val finalDrinkReminderTimes = mutableListOf<LocalTime>(startOffsetTime.toLocalTime())
        while (start.plusMinutes(90).isBefore(end)) {
            start = start.plusMinutes(90)
            startOffsetTime = startOffsetTime.plusMinutes(90)
            finalDrinkReminderTimes.add(startOffsetTime.toLocalTime())
        }
        
        return finalDrinkReminderTimes
    }
}