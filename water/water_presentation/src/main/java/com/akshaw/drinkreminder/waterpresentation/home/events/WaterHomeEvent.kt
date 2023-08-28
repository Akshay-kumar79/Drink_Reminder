package com.akshaw.drinkreminder.waterpresentation.home.events

import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink

sealed interface WaterHomeEvent{
    data class OnTrackableDrinkChange(val drink: TrackableDrink): WaterHomeEvent
    object OnDrinkClick: WaterHomeEvent
    data class OnDrinkDeleteClick(val drink: com.akshaw.drinkreminder.waterdomain.model.Drink): WaterHomeEvent
    object RestoreDrink: WaterHomeEvent
}
