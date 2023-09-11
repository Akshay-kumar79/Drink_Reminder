package com.akshaw.drinkreminder.core.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.util.ReminderType
import com.akshaw.drinkreminder.core.domain.mapper.toDrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import com.akshaw.drinkreminder.core.domain.use_case.RescheduleAllTSDrinkReminders
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
    lateinit var rescheduleAllTSDrinkReminders: RescheduleAllTSDrinkReminders
    
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            
            /** Re-Schedule all reminder only if reminder type is set to [ReminderType.TSReminder] */
            // TODO change value to get from preference to check reminder type
            val isReminderTypeTS = true
            if (isReminderTypeTS) {
                val pendingResult = goAsync()
                GlobalScope.launch(Dispatchers.IO) {
                    rescheduleAllTSDrinkReminders()
                    pendingResult.finish()
                }
            }
        }
    }
}