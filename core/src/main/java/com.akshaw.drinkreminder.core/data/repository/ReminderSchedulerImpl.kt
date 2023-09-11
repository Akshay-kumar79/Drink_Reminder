package com.akshaw.drinkreminder.core.data.repository

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.akshaw.drinkreminder.core.data.receivers.ReminderReceiver
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.TimeZone

/**
 *  AlarmManager wrapper to schedule reminders for water intake
 */
class ReminderSchedulerImpl(private val context: Context) : ReminderScheduler {
    
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    
    override fun schedule(drinkReminder: DrinkReminder): Result<LocalDateTime> {
        if (drinkReminder.isReminderOn && drinkReminder.activeDays.isNotEmpty()) {
            
            val intent = Intent(context, ReminderReceiver::class.java)
            intent.putExtra(INTENT_DRINK_REMINDER_KEY, drinkReminder.encodeToJsonString())
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                drinkReminder.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
            
            val nextAlarmTime = getNextAlarmTime(drinkReminder)
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) {
                return Result.failure(NoNotificationPermissionException())
            }
            
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextAlarmTime,
                    pendingIntent
                )
            } catch (e: Exception) {
                return Result.failure(NoExactAlarmPermissionException())
            }
            
            return Result.success(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(nextAlarmTime), TimeZone.getDefault().toZoneId())
            )
        }
        return Result.failure(ReminderOffException())
    }
    
    override fun cancel(drinkReminder: DrinkReminder) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            drinkReminder.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        
        try {
            alarmManager.cancel(pendingIntent)
        } catch (e: Exception) {
        }
    }
    
    companion object {
        
        const val INTENT_DRINK_REMINDER_KEY = "drink_reminder"
        
        /**
         * @return most recent upcoming alarm UNIX time in regards with [DrinkReminder.activeDays].
         *
         *  Its a business logic and i don't know where it should be. As i need this logic in data layer,
         *  putting here temporarily util i don't understand clean architecture for android completely
         */
        private fun getNextAlarmTime(drinkReminder: DrinkReminder): Long {
            
            val drinkReminderTime = drinkReminder.time.withSecond(0).withNano(0)
            val currentTime = LocalTime.now().withSecond(0).withNano(0)
            
            val nextAlarmDateTime = if (drinkReminderTime.isBefore(currentTime) || drinkReminderTime.equals(currentTime)) {
                LocalDateTime.now()
                    .plusDays(1)
                    .withHour(drinkReminder.time.hour)
                    .withMinute(drinkReminder.time.minute)
                    .withSecond(0)
                    .withNano(0)
            } else {
                LocalDateTime.now()
                    .withHour(drinkReminder.time.hour)
                    .withMinute(drinkReminder.time.minute)
                    .withSecond(0)
                    .withNano(0)
            }.run {
                var dateTime = this
                // keep forwarding the day until the day in not in list of activeDays in DrinkReminder (to make sure reminder only buzz on active days)
                while (!drinkReminder.activeDays.contains(dateTime.dayOfWeek)) {

                    // loop should not run more than 7 time as there is only 7 days a week
                    dateTime = dateTime.plusDays(1)
                }
                
                dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }
            
            return nextAlarmDateTime
        }
    }
    
    inner class NoExactAlarmPermissionException : Exception("Permission not allowed to set exact alarm")
    inner class NoNotificationPermissionException : Exception("Permission not allowed to show notification")
    inner class ReminderOffException : Exception("Reminder is off or no day is selected")
}