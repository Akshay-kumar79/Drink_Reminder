package com.akshaw.drinkreminder.waterdata.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.akshaw.drinkreminder.core.domain.MyNotification
import com.akshaw.drinkreminder.core.domain.MyNotificationManager
import com.akshaw.drinkreminder.waterdata.repository.ReminderSchedulerImpl
import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var reminderScheduler: ReminderScheduler
    
    @Inject
    lateinit var myNotificationManager: MyNotificationManager
    
    override fun onReceive(context: Context?, intent: Intent?) {
        
        val drinkReminder = intent?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(ReminderSchedulerImpl.INTENT_DRINK_REMINDER_KEY, DrinkReminder::class.java)
            } else {
                intent.getParcelableExtra(ReminderSchedulerImpl.INTENT_DRINK_REMINDER_KEY)
            }
        } ?: return
        
        myNotificationManager.notify(MyNotification.DrinkNotification)
        reminderScheduler.schedule(drinkReminder)
    }
    
}