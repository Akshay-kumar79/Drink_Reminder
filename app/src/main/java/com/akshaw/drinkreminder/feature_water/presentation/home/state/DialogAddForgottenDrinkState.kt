package com.akshaw.drinkreminder.feature_water.presentation.home.state

import java.time.LocalTime

data class DialogAddForgottenDrinkState(
    val isDialogShowing: Boolean = false,
    val quantity: String = "",
    val hour: Int = LocalTime.now().hour,
    val minute: Int = LocalTime.now().minute,
)
