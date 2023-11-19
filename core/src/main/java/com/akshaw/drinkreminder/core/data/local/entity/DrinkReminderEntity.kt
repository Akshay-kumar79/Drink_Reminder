package com.akshaw.drinkreminder.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_reminder_table")
data class DrinkReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    
    var time: String,
    var isReminderOn: Boolean = true,
    var isSunActive: Boolean = true,
    var isMonActive: Boolean = true,
    var isTueActive: Boolean = true,
    var isWedActive: Boolean = true,
    var isThuActive: Boolean = true,
    var isFriActive: Boolean = true,
    var isSatActive: Boolean = true
)