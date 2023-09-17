package com.akshaw.drinkreminder.corecompose.events

sealed interface NotificationPermissionDialogEvent{
    
    object ShowDialog: NotificationPermissionDialogEvent
    object DismissDialog: NotificationPermissionDialogEvent
    
}