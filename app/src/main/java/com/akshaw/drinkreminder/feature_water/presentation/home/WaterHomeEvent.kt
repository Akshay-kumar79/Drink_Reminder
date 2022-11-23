package com.akshaw.drinkreminder.feature_water.presentation.home

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

sealed interface WaterHomeEvent{
    object OnReminderClick: WaterHomeEvent
    data class OnTrackableDrinkChange(val drink: TrackableDrink): WaterHomeEvent
    object OnDrinkClick: WaterHomeEvent
    object OnAddTrackableDrinkClick: WaterHomeEvent
    object OnAddTrackableDrinkConfirm: WaterHomeEvent
    object OnAddTrackableDrinkCancel: WaterHomeEvent
    data class OnAddQuantityAmountChange(val amount: String): WaterHomeEvent
    object OnRemoveTrackableDrinkClick: WaterHomeEvent
    object OnRemoveTrackableDrinkConfirm: WaterHomeEvent
    object OnRemoveTrackableDrinkCancel: WaterHomeEvent
    object OnAddForgotDrinkClick: WaterHomeEvent
    data class OnAddForgotDrinkConfirm(val drink: Drink): WaterHomeEvent
    object OnAddForgotDrinkCancel: WaterHomeEvent
}
