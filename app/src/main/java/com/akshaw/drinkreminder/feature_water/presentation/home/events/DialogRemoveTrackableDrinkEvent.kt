package com.akshaw.drinkreminder.feature_water.presentation.home.events

sealed interface DialogRemoveTrackableDrinkEvent{
    object OnRemoveTrackableDrinkClick: DialogRemoveTrackableDrinkEvent
    object OnConfirmClick: DialogRemoveTrackableDrinkEvent
    object OnDismiss: DialogRemoveTrackableDrinkEvent
}