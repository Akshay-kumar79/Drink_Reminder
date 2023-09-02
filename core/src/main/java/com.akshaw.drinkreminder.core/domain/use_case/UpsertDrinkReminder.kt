package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  Add [DrinkReminder] to database
 */
class UpsertDrinkReminder @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(drinkReminder: DrinkReminder): Long = withContext(Dispatchers.IO) {
        reminderRepository.upsertDrinkReminder(
            drinkReminder.copy(
                time = drinkReminder.time.withSecond(0).withNano(0)
            )
        )
    }
    
}