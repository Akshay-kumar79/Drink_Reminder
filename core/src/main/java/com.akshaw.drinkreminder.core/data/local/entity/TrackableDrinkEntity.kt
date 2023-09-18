package com.akshaw.drinkreminder.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trackable_drinks_table")
data class TrackableDrinkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    var amount: Double,
    var unit: String
)
