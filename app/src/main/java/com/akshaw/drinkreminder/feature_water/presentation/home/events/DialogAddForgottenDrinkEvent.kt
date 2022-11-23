package com.akshaw.drinkreminder.feature_water.presentation.home.events

sealed interface DialogAddForgottenDrinkEvent {
    object OnAddForgotDrinkClick : DialogAddForgottenDrinkEvent
    object OnConfirmClick : DialogAddForgottenDrinkEvent
    object OnCancelClick : DialogAddForgottenDrinkEvent
    data class OnQuantityAmountChange(val amount: String) : DialogAddForgottenDrinkEvent
    data class OnHourChange(val hour: Int) : DialogAddForgottenDrinkEvent
    data class OnMinuteChange(val minute: Int) : DialogAddForgottenDrinkEvent
}