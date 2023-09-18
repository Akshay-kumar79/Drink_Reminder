package com.akshaw.drinkreminder.core.domain.mapper

import com.akshaw.drinkreminder.core.data.local.entity.TrackableDrinkEntity
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit

fun TrackableDrinkEntity.toTrackableDrink(): TrackableDrink {
    return TrackableDrink(
        id = id,
        amount = amount,
        unit = WaterUnit.fromString(unit)
    )
}

fun TrackableDrink.toTrackableDrinkEntity(): TrackableDrinkEntity{
    return TrackableDrinkEntity(
        id = id,
        amount = amount,
        unit = unit.text
    )
}