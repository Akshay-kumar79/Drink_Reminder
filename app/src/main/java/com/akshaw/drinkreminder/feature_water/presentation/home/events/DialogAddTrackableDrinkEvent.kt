package com.akshaw.drinkreminder.feature_water.presentation.home.events

sealed interface DialogAddTrackableDrinkEvent{
    object OnAddTrackableDrinkClick: DialogAddTrackableDrinkEvent
    object OnConfirmClick: DialogAddTrackableDrinkEvent
    object OnDismiss: DialogAddTrackableDrinkEvent
    data class OnQuantityAmountChange(val amount: String): DialogAddTrackableDrinkEvent
}