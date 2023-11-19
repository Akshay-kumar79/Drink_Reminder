package com.akshaw.drinkreminder.core.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.akshaw.drinkreminder.core.domain.preferences.elements.ReminderType
import com.akshaw.drinkreminder.core.domain.use_case.RescheduleAllTSDrinkReminders
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimeChangeReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var rescheduleAllTSDrinkReminders: RescheduleAllTSDrinkReminders
    
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        
        val action = intent.action
        if (Intent.ACTION_TIME_CHANGED == action || Intent.ACTION_TIMEZONE_CHANGED == action) {
            
            /** Cancel and Re-Schedule all reminder only if reminder type is set to [ReminderType.TSReminder] */
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