package com.akshaw.drinkreminder.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks_table")
data class DrinkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var milli: Long = System.currentTimeMillis(),
    var waterIntake: Double,
    var unit: String
)