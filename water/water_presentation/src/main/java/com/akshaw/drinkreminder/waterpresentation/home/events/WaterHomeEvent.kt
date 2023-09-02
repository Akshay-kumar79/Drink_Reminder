package com.akshaw.drinkreminder.waterpresentation.home.events

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink

sealed interface WaterHomeEvent{
    data class OnTrackableDrinkChange(val drink: TrackableDrink): WaterHomeEvent
    object OnDrinkClick: WaterHomeEvent
    data class OnDrinkDeleteClick(val drink: Drink): WaterHomeEvent
    object RestoreDrink: WaterHomeEvent
}
