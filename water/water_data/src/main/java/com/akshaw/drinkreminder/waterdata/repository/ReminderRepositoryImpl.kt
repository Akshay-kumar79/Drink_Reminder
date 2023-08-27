package com.akshaw.drinkreminder.waterdata.repository

import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.waterdomain.mapper.toDrinkReminder
import com.akshaw.drinkreminder.waterdomain.mapper.toDrinkReminderEntity
import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.repository.ReminderRepository
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