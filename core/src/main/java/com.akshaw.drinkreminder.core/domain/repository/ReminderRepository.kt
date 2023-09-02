package com.akshaw.drinkreminder.core.domain.repository

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    
    suspend fun upsertDrinkReminder(drinkReminder: DrinkReminder): Long
    fun getAllDrinkReminders(): Flow<List<DrinkReminder>>
    suspend fun removeDrinkReminder(drinkReminder: DrinkReminder)
    
}