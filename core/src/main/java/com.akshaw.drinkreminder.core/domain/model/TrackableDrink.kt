package com.akshaw.drinkreminder.core.domain.model

import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit

data class TrackableDrink(
    var id: Long? = null,
    var amount: Double,
    var unit: WaterUnit
)
