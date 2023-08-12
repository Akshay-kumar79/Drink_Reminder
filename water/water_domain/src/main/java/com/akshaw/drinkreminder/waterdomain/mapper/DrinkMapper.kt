package com.akshaw.drinkreminder.waterdomain.mapper

import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.model.Drink
import java.time.Instant
import java.time.ZoneId

fun DrinkEntity.toDrink(): com.akshaw.drinkreminder.waterdomain.model.Drink {
    return com.akshaw.drinkreminder.waterdomain.model.Drink(
        id = id,
        dateTime = Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDateTime(),
        waterIntake = waterIntake,
        unit = WaterUnit.fromString(unit)
    )
}

fun com.akshaw.drinkreminder.waterdomain.model.Drink.toDrinkEntity(): DrinkEntity{
    return DrinkEntity(
        id = id,
        milli = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        waterIntake = waterIntake,
        unit = unit.name
    )
}