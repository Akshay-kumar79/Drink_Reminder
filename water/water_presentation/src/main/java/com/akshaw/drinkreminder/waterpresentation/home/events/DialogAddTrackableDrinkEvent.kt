package com.akshaw.drinkreminder.waterpresentation.home.events

sealed interface DialogAddTrackableDrinkEvent{
    object OnAddTrackableDrinkClick: DialogAddTrackableDrinkEvent
    object OnConfirmClick: DialogAddTrackableDrinkEvent
    object OnDismiss: DialogAddTrackableDrinkEvent
    data class OnQuantityAmountChange(val amount: String): DialogAddTrackableDrinkEvent
}