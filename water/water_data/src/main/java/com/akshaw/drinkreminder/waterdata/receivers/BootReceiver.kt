package com.akshaw.drinkreminder.waterdata.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.util.ReminderType
import com.akshaw.drinkreminder.waterdomain.mapper.toDrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var reminderScheduler: ReminderScheduler
    
    @Inject
    lateinit var database: MyDatabase
    
    private val drinkReminderDao = database.drinkReminderDao
    
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            
            /** Re-Schedule all reminder only if reminder type is set to [ReminderType.TSReminder] */
            // TODO change value to get from preference to check reminder type
            val isReminderTypeTS = true
            if (isReminderTypeTS) {
                receiverScope.launch(Dispatchers.IO) {
                    drinkReminderDao.getAllDrinkReminders().first()
                        .forEach {
                            reminderScheduler.schedule(it.toDrinkReminder())
                        }
                }
            }
            
        }
    }
    
    
}

/**
 *  Will not run longer than 10s
 */
@OptIn(DelicateCoroutinesApi::class)
val BroadcastReceiver.receiverScope: GlobalScope
    get() = GlobalScope.also {
        goAsync()
    }
