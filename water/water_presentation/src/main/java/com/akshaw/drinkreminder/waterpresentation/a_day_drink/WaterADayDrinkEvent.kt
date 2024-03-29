package com.akshaw.drinkreminder.waterpresentation.a_day_drink

import com.akshaw.drinkreminder.core.domain.model.Drink

sealed interface WaterADayDrinkEvent{
    data class OnDrinkDeleteClick(val drink: Drink): WaterADayDrinkEvent
    object RestoreDrink: WaterADayDrinkEvent
}