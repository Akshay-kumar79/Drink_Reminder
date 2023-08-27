package com.akshaw.drinkreminder.settingspresentation.settings.events

sealed interface ChangeWakeupTimeDialogEvent {
    
    object ShowDialog : ChangeWakeupTimeDialogEvent
    object DismissDialog : ChangeWakeupTimeDialogEvent
    data class OnHourChange(val hour: Int) : ChangeWakeupTimeDialogEvent
    data class OnMinuteChange(val minute: Int) : ChangeWakeupTimeDialogEvent
    object SaveNewWakeupTime: ChangeWakeupTimeDialogEvent
    
}