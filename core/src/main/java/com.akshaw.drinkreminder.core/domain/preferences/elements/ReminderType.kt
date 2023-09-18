package com.akshaw.drinkreminder.core.domain.preferences.elements

/**
 *  - [ReminderType.TSReminder] Time Specific Reminders
 *  - [ReminderType.AIReminder] Automatic Interval Reminders
 */
enum class ReminderType {
    TSReminder,
    AIReminder
}

/**
 *  return title to show in list
 */
fun ReminderType.getText(): String {
    return when (this) {
        ReminderType.TSReminder -> "Time Specific Reminder"
        ReminderType.AIReminder -> "Automatic Interval Reminder (Optimized to save battery)"
    }
}

fun ReminderType.getDescription(): String {
    return when (this) {
        ReminderType.TSReminder -> "Trigger the reminder on exact time"
        ReminderType.AIReminder -> "Trigger the reminder automatically at best time suited with android phones doze mode at specified interval"
    }
}