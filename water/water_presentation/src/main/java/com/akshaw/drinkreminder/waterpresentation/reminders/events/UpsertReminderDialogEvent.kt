package com.akshaw.drinkreminder.waterpresentation.reminders.events

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import java.time.DayOfWeek

sealed interface UpsertReminderDialogEvent{
    
    data class ShowDialog(val reminderMode: TSReminderMode): UpsertReminderDialogEvent
    object DismissDialog: UpsertReminderDialogEvent
    data class OnHourChange(val hour: Int): UpsertReminderDialogEvent
    data class OnMinuteChange(val minute: Int): UpsertReminderDialogEvent
    data class OnChangeDayOfWeeks(val dayOfWeek: DayOfWeek): UpsertReminderDialogEvent
    object OnDoneClick: UpsertReminderDialogEvent
    
}


/** Time Specific reminder mode */
sealed interface TSReminderMode{
    object AddNewReminder: TSReminderMode
    data class UpdateReminder(val drinkReminder: DrinkReminder): TSReminderMode
}