package com.akshaw.drinkreminder.corecompose.theme.events

sealed interface ExactAlarmPermissionDialogEvent{
    
    object ShowDialog: ExactAlarmPermissionDialogEvent
    object DismissDialog: ExactAlarmPermissionDialogEvent
    
}