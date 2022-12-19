package com.akshaw.drinkreminder.feature_water.domain.mapper

import com.akshaw.drinkreminder.core.data.local.entity.TrackableDrinkEntity
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink

fun TrackableDrinkEntity.toTrackableDrink(): TrackableDrink{
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
        unit = unit.name
    )
}