package com.akshaw.drinkreminder.feature_water.presentation.home.events

sealed interface DialogAddTrackableDrinkEvent{
    object OnAddTrackableDrinkClick: DialogAddTrackableDrinkEvent
    object OnConfirmClick: DialogAddTrackableDrinkEvent
    object OnCancelClick: DialogAddTrackableDrinkEvent
    data class OnQuantityAmountChange(val amount: String): DialogAddTrackableDrinkEvent
}