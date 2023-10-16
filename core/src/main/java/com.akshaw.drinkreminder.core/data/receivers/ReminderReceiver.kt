package com.akshaw.drinkreminder.core.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.akshaw.drinkreminder.core.domain.MyNotification
import com.akshaw.drinkreminder.core.domain.MyNotificationManager
import com.akshaw.drinkreminder.core.data.repository.ReminderSchedulerImpl
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var reminderScheduler: ReminderScheduler
    
    @Inject
    lateinit var myNotificationManager: MyNotificationManager
    
    override fun onReceive(context: Context?, intent: Intent?) {
        
        val drinkReminder = intent?.let {
            it.getStringExtra(ReminderSchedulerImpl.INTENT_DRINK_REMINDER_KEY)?.let { jsonDrinkReminder ->
                DrinkReminder.decodeFromJsonString(jsonDrinkReminder)
            }
            
        } ?: return
        
        myNotificationManager.notify(MyNotification.DrinkNotification)
        reminderScheduler.schedule(drinkReminder)
    }
    
}