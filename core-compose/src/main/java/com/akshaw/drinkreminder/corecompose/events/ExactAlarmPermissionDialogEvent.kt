package com.akshaw.drinkreminder.corecompose.events

sealed interface ExactAlarmPermissionDialogEvent{
    
    object ShowDialog: ExactAlarmPermissionDialogEvent
    object DismissDialog: ExactAlarmPermissionDialogEvent
    
}