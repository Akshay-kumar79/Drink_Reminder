package com.akshaw.drinkreminder.core.domain.mapper

import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.Instant
import java.time.ZoneId

fun DrinkEntity.toDrink(): Drink {
    return Drink(
        id = id,
        dateTime = Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDateTime(),
        waterIntake = waterIntake,
        unit = WaterUnit.fromString(unit)
    )
}

fun Drink.toDrinkEntity(): DrinkEntity{
    return DrinkEntity(
        id = id,
        milli = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        waterIntake = waterIntake,
        unit = unit.name
    )
}