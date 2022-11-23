package com.akshaw.drinkreminder.feature_water.presentation.home.state

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

data class WaterHomeState(
    val goal: Double = 2343.0,
    val progress: Double = 0.0,
    val waterUnit: WaterUnit = Constants.DEFAULT_WATER_UNIT,
    val selectedTrackableDrink: TrackableDrink = TrackableDrink(-1, 0.0, WaterUnit.ML),
    val trackableDrinks: List<TrackableDrink> = emptyList(),
    val drinks: List<Drink> = emptyList(),
    val isRemoveTrackableDrinkDialogShowing: Boolean = false
)
