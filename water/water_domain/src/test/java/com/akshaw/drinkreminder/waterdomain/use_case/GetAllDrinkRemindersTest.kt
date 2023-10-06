package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class GetAllDrinkRemindersTest {
    
    private lateinit var getAllDrinkReminder: GetAllDrinkReminders
    private lateinit var reminderRepository: FakeReminderRepository
    
    @BeforeEach
    fun setUp() {
        reminderRepository = FakeReminderRepository()
        getAllDrinkReminder = GetAllDrinkReminders(reminderRepository)
    }
    
    @Test
    fun `returns all drink reminders that is available`() = runBlocking {
        
        val drinkReminders = mutableListOf<DrinkReminder>()
        
        for (i in 1..5) {
            val reminder = DrinkReminder(
                id = i.toLong(),
                time = LocalTime.now().plusMinutes(i.toLong()),
                isReminderOn = true,
                activeDays = DayOfWeek.values().toList()
            )
            drinkReminders.add(reminder)
            reminderRepository.upsertDrinkReminder(reminder)
        }
        
        val allDrinkReminders = getAllDrinkReminder()
        assertThat(allDrinkReminders.first().size).isEqualTo(5)
        drinkReminders.forEach { reminder ->
            assertThat(allDrinkReminders.first().contains(reminder)).isTrue()
        }
    }
    
    @Test
    fun `returns 0 drink reminders if there is no reminder`() = runBlocking {
        assertThat(getAllDrinkReminder().first().size).isEqualTo(0)
    }
}