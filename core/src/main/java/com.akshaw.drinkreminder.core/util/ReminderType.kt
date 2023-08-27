package com.akshaw.drinkreminder.core.util

/**
 * Changing these constant value may cause compatibility issues with older versions of app
 * as these values are stored in local preferences
 */
private const val TIME_SPECIFIC_REMINDER = "time_specific_reminder"
private const val AUTOMATIC_INTERVAL_REMINDER = "automatic_interval_reminder"
private const val INVALID = "invalid"

sealed class ReminderType(val name: String) {
    
    object TSReminder : ReminderType(TIME_SPECIFIC_REMINDER)
    object AIReminder : ReminderType(AUTOMATIC_INTERVAL_REMINDER)
    object Invalid : ReminderType(INVALID)
    
    companion object {
        fun fromString(text: String?): ReminderType {
            return when (text) {
                TIME_SPECIFIC_REMINDER -> TSReminder
                AUTOMATIC_INTERVAL_REMINDER -> AIReminder
                else -> Invalid
            }
        }
    }
    
    /**
     *  return title to show in list
     */
    fun getText(): String{
        return when(this){
            TSReminder -> "Time Specific Reminder"
            AIReminder -> "Automatic Interval Reminder (Optimized to save battery)"
            Invalid -> "invalid"
        }
    }
    
    fun getDescription(): String{
        return when(this){
            TSReminder -> "Trigger the reminder on exact time"
            AIReminder -> "Trigger the reminder automatically at best time suited with android phones doze mode at specified interval"
            Invalid -> "invalid"
        }
    }
}