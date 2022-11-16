package com.akshaw.drinkreminder.feature_water.presentation.home

import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

sealed interface WaterHomeEvent{
    object OnReminderClick: WaterHomeEvent
    object OnRemoveTrackableDrinkClick: WaterHomeEvent
    object OnAddTrackableDrinkClick: WaterHomeEvent
    data class OnTrackableDrinkChange(val drink: TrackableDrink): WaterHomeEvent
    object OnDrinkClick: WaterHomeEvent
    object OnForgotDrinkClick: WaterHomeEvent
}
