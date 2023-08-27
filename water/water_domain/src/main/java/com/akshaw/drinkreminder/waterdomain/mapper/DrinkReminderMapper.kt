package com.akshaw.drinkreminder.waterdomain.mapper

import com.akshaw.drinkreminder.core.data.local.entity.DrinkReminderEntity
import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

fun DrinkReminderEntity.toDrinkReminder(): DrinkReminder {
    return DrinkReminder(
        id = id,
        time = LocalTime.parse(time, timeFormatter),
        isReminderOn = isReminderOn,
        activeDays = mutableListOf<DayOfWeek>().apply {
            if (isSunActive) {
                add(DayOfWeek.SUNDAY)
            }
            if (isMonActive) {
                add(DayOfWeek.MONDAY)
            }
            if (isTueActive) {
                add(DayOfWeek.TUESDAY)
            }
            if (isWedActive) {
                add(DayOfWeek.WEDNESDAY)
            }
            if (isThuActive) {
                add(DayOfWeek.THURSDAY)
            }
            if (isFriActive) {
                add(DayOfWeek.FRIDAY)
            }
            if (isSatActive) {
                add(DayOfWeek.SATURDAY)
            }
        }
    )
}

fun DrinkReminder.toDrinkReminderEntity(): DrinkReminderEntity {
    return DrinkReminderEntity(
        id = id,
        time = time.format(timeFormatter),
        isReminderOn = isReminderOn,
        isSunActive = activeDays.contains(DayOfWeek.SUNDAY),
        isMonActive = activeDays.contains(DayOfWeek.MONDAY),
        isTueActive = activeDays.contains(DayOfWeek.TUESDAY),
        isWedActive = activeDays.contains(DayOfWeek.WEDNESDAY),
        isThuActive = activeDays.contains(DayOfWeek.THURSDAY),
        isFriActive = activeDays.contains(DayOfWeek.FRIDAY),
        isSatActive = activeDays.contains(DayOfWeek.SATURDAY)
    )
}