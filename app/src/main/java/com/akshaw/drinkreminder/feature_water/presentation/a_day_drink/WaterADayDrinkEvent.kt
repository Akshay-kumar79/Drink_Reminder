package com.akshaw.drinkreminder.feature_water.presentation.a_day_drink

import com.akshaw.drinkreminder.feature_water.domain.model.Drink

sealed interface WaterADayDrinkEvent{
    data class OnDrinkDeleteClick(val drink: Drink): WaterADayDrinkEvent
    object RestoreDrink: WaterADayDrinkEvent
}