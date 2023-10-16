package com.akshaw.drinkreminder.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.akshaw.drinkreminder.core.data.local.entity.DrinkReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkReminderDao {
    
    @Upsert
    fun upsertDrinkReminder(drinkReminderEntity: DrinkReminderEntity): Long
    
    @Query("select * from drink_reminder_table")
    fun getAllDrinkReminders(): Flow<List<DrinkReminderEntity>>
    
    @Delete
    fun removeDrinkReminder(drinkReminderEntity: DrinkReminderEntity)
    
}