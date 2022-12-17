package com.akshaw.drinkreminder.feature_water.presentation.common.events

sealed interface DialogAddForgottenDrinkEvent {
    object OnAddForgotDrinkClick : DialogAddForgottenDrinkEvent
    object OnConfirmClick : DialogAddForgottenDrinkEvent
    object OnDismiss : DialogAddForgottenDrinkEvent
    data class OnQuantityAmountChange(val amount: String) : DialogAddForgottenDrinkEvent
    data class OnHourChange(val hour: Int) : DialogAddForgottenDrinkEvent
    data class OnMinuteChange(val minute: Int) : DialogAddForgottenDrinkEvent
}