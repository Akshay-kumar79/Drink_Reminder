package com.akshaw.drinkreminder.core.data.repository

import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.domain.mapper.toDrinkReminder
import com.akshaw.drinkreminder.core.domain.mapper.toDrinkReminderEntity
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepositoryImpl(
    database: MyDatabase
) : ReminderRepository {
    
    private val drinkReminderDao = database.drinkReminderDao
    
    override suspend fun upsertDrinkReminder(drinkReminder: DrinkReminder): Long {
        return drinkReminderDao.upsertDrinkReminder(drinkReminder.toDrinkReminderEntity())
    }
    
    override fun getAllDrinkReminders(): Flow<List<DrinkReminder>> {
        return drinkReminderDao.getAllDrinkReminders().map {
            it.map { it.toDrinkReminder() }
        }
    }
    
    override suspend fun removeDrinkReminder(drinkReminder: DrinkReminder) {
        drinkReminderDao.removeDrinkReminder(drinkReminder.toDrinkReminderEntity())
    }
}