package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.util.truncateToMinutes
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class UpsertDrinkReminderTest {
    
    private lateinit var upsertDrinkReminder: UpsertDrinkReminder
    private lateinit var reminderRepository: FakeReminderRepository
    
    @BeforeEach
    fun setUp() {
        reminderRepository = FakeReminderRepository()
        upsertDrinkReminder = UpsertDrinkReminder(reminderRepository)
    }
    
    @Test
    fun `add new drink reminder test`() = runBlocking {
        val drinkReminder = DrinkReminder(
            0,
            LocalTime.of(2, 2, 2, 2),
            true,
            DayOfWeek.values().toList()
        )
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(0)
        
        val id = upsertDrinkReminder(drinkReminder)
        
        assertThat(id).isEqualTo(drinkReminder.id)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder.copy(time = drinkReminder.time.truncateToMinutes()))
    }
    
    
    @Test
    fun `update drink reminder test`() = runBlocking {
        val drinkReminder = DrinkReminder(
            0,
            LocalTime.of(2, 2, 2, 2),
            true,
            DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        
        val id = upsertDrinkReminder(drinkReminder.copy(isReminderOn = false))
        
        assertThat(id).isEqualTo(drinkReminder.id)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder.copy(
            time = drinkReminder.time.truncateToMinutes(),
            isReminderOn = false
        ))
    }
    
    
    @Test
    fun `add new drink reminder when one other already exist`() = runBlocking {
        val drinkReminder = DrinkReminder(
            0,
            LocalTime.of(2, 2, 2, 2),
            true,
            DayOfWeek.values().toList()
        )
    
        reminderRepository.drinkReminders.add(DrinkReminder(
            1, LocalTime.now(), false, DayOfWeek.values().toList()
        ))
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
    
        val id = upsertDrinkReminder(drinkReminder)
    
        assertThat(id).isEqualTo(drinkReminder.id)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(2)
        assertThat(reminderRepository.drinkReminders.find { it.id == drinkReminder.id }!!).isEqualTo(drinkReminder.copy(
            time = drinkReminder.time.truncateToMinutes()
        ))
    }
    
}