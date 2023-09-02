package com.akshaw.drinkreminder.waterpresentation.reminders.events

import com.akshaw.drinkreminder.core.util.ReminderType
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder

sealed interface RemindersEvent {
    
    data class OnReminderSwitched(val drinkReminder: DrinkReminder, val isReminderOn: Boolean) : RemindersEvent
    data class OnSelectedReminderTypeChange(val reminderType: ReminderType): RemindersEvent
    data class OnReminderTypeDropdownExpandedChange(val isShowing: Boolean): RemindersEvent
    data class OnDeleteReminder(val drinkReminder: DrinkReminder): RemindersEvent
    
}