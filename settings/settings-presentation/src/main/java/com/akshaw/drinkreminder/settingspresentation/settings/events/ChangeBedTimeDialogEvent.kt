package com.akshaw.drinkreminder.settingspresentation.settings.events

sealed interface ChangeBedTimeDialogEvent {
    
    object ShowDialog : ChangeBedTimeDialogEvent
    object DismissDialog : ChangeBedTimeDialogEvent
    data class OnHourChange(val hour: Int) : ChangeBedTimeDialogEvent
    data class OnMinuteChange(val minute: Int) : ChangeBedTimeDialogEvent
    object SaveNewBedTime: ChangeBedTimeDialogEvent
    
}