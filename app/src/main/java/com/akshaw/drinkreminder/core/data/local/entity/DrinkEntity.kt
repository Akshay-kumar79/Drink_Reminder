package com.akshaw.drinkreminder.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akshaw.drinkreminder.core.util.Constants
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "drinks_table")
data class DrinkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    var milli: Long = System.currentTimeMillis(),
    var waterIntake: Double,
    var unit: String
)