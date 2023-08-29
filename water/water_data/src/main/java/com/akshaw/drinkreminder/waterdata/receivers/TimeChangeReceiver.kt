package com.akshaw.drinkreminder.waterdata.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.util.ReminderType
import com.akshaw.drinkreminder.waterdomain.mapper.toDrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimeChangeReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var reminderScheduler: ReminderScheduler
    
    @Inject
    lateinit var database: MyDatabase
    
    private val drinkReminderDao = database.drinkReminderDao
    
    override fun onReceive(context: Context, intent: Intent) {
        
        val action = intent.action
        if (Intent.ACTION_TIME_CHANGED == action || Intent.ACTION_TIMEZONE_CHANGED == action) {
    
            /** Cancel and Re-Schedule all reminder only if reminder type is set to [ReminderType.TSReminder] */
            // TODO change value to get from preference to check reminder type
            val isReminderTypeTS = true
            if (isReminderTypeTS) {
                receiverScope.launch(Dispatchers.IO) {
                    drinkReminderDao.getAllDrinkReminders().first()
                        .forEach {
                            
                            // alarms should automatically updated in schedule method
                            // but still cancelling here just to make sure
                            reminderScheduler.cancel(it.toDrinkReminder())
                            reminderScheduler.schedule(it.toDrinkReminder())
                        }
                }
            }
        }
    }
}