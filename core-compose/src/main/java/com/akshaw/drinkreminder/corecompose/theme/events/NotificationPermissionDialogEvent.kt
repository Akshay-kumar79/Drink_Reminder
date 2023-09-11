package com.akshaw.drinkreminder.corecompose.theme.events

sealed interface NotificationPermissionDialogEvent{
    
    object ShowDialog: NotificationPermissionDialogEvent
    object DismissDialog: NotificationPermissionDialogEvent
    
}