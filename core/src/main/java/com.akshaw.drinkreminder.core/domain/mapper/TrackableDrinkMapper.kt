package com.akshaw.drinkreminder.core.domain.mapper

import com.akshaw.drinkreminder.core.data.local.entity.TrackableDrinkEntity
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.util.Constants

fun TrackableDrinkEntity.toTrackableDrink(): TrackableDrink {
    return TrackableDrink(
        id = id,
        amount = amount,
        unit = WaterUnit.fromString(unit) ?: Constants.DEFAULT_WATER_UNIT
    )
}

fun TrackableDrink.toTrackableDrinkEntity(): TrackableDrinkEntity{
    return TrackableDrinkEntity(
        id = id,
        amount = amount,
        unit = unit.text
    )
}