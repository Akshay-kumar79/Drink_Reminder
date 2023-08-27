package com.akshaw.drinkreminder.waterdomain.repository

import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    
    suspend fun upsertDrinkReminder(drinkReminder: DrinkReminder): Long
    fun getAllDrinkReminders(): Flow<List<DrinkReminder>>
    suspend fun removeDrinkReminder(drinkReminder: DrinkReminder)
    
}