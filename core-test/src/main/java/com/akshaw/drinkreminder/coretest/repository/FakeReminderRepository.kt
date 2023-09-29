package com.akshaw.drinkreminder.coretest.repository

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeReminderRepository : ReminderRepository {
    
    val drinkReminders = mutableListOf<DrinkReminder>()
    
    override suspend fun upsertDrinkReminder(drinkReminder: DrinkReminder): Long {
        drinkReminders.removeIf { it.id == drinkReminder.id }
        drinkReminders.add(drinkReminder)
        return drinkReminder.id
    }
    
    override fun getAllDrinkReminders(): Flow<List<DrinkReminder>> {
        // returning copy so we don't get concurrency issue when updating drinkReminders
        // while iterating through getAllDrinkReminders()
        val drinkRemindersCopy = mutableListOf<DrinkReminder>()
        drinkReminders.forEach {
            drinkRemindersCopy.add(it.copy())
        }
        return flow {
            emit(drinkRemindersCopy)
        }
    }
    
    override suspend fun removeDrinkReminder(drinkReminder: DrinkReminder) {
        drinkReminders.removeIf { it.id == drinkReminder.id }
    }
}