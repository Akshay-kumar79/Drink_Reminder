package com.akshaw.drinkreminder.waterpresentation.home.events

sealed interface DialogRemoveTrackableDrinkEvent{
    object OnRemoveTrackableDrinkClick: DialogRemoveTrackableDrinkEvent
    object OnConfirmClick: DialogRemoveTrackableDrinkEvent
    object OnDismiss: DialogRemoveTrackableDrinkEvent
}