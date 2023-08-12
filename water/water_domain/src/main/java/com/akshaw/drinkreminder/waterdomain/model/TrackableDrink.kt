package com.akshaw.drinkreminder.waterdomain.model

import com.akshaw.drinkreminder.core.util.WaterUnit

data class TrackableDrink(
    var id: Long? = null,
    var amount: Double,
    var unit: WaterUnit
)
