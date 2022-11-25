package com.akshaw.drinkreminder.feature_water.presentation.home.events

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

sealed interface WaterHomeEvent{
    object OnReminderClick: WaterHomeEvent
    data class OnTrackableDrinkChange(val drink: TrackableDrink): WaterHomeEvent
    object OnDrinkClick: WaterHomeEvent
    data class OnDrinkDeleteClick(val drink: Drink): WaterHomeEvent
    object RestoreDrink: WaterHomeEvent
}
