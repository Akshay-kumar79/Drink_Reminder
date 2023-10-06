package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class DeleteDrinkReminderTest {
    
    private lateinit var deleteDrinkReminder: DeleteDrinkReminder
    private lateinit var reminderRepository: FakeReminderRepository
    
    @BeforeEach
    fun setUp() {
        reminderRepository = FakeReminderRepository()
        deleteDrinkReminder = DeleteDrinkReminder(reminderRepository)
    }
    
    
    @Test
    fun `delete drink reminder test`() = runBlocking {
        val drinkReminder = DrinkReminder(
            time = LocalTime.now(),
            isReminderOn = true,
            activeDays = DayOfWeek.values().toList()
        )
    
        reminderRepository.drinkReminders.add(drinkReminder)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
    
        deleteDrinkReminder(drinkReminder)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(0)
    }
}