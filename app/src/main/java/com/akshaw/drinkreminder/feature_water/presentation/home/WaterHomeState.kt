package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.compose.ui.unit.Constraints
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

data class WaterHomeState(
    val goal: Double = 2343.0,
    val progress: Double = 0.0,
    val selectedTrackableDrink: TrackableDrink = TrackableDrink(null,0.0, Constants.DEFAULT_WATER_UNIT),
    val trackableDrinks: List<TrackableDrink> = emptyList(),
    val drinks: List<Drink> = emptyList()
)
