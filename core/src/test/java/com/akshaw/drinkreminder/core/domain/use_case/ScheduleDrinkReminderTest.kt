package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.util.truncateToMinutes
import com.akshaw.drinkreminder.coretest.repository.FakeReminderScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class ScheduleDrinkReminderTest {
    
    private lateinit var scheduleDrinkReminder: ScheduleDrinkReminder
    private lateinit var reminderScheduler: FakeReminderScheduler
    
    @BeforeEach
    fun setUp() {
        reminderScheduler = FakeReminderScheduler()
        scheduleDrinkReminder = ScheduleDrinkReminder(reminderScheduler)
    }
    
    
    @Test
    fun `schedule drink reminder test`() {
        
        val drinkReminder = DrinkReminder(
            0,
             LocalTime.of(2,2,2,2),
            true,
            DayOfWeek.values().toList()
        )
        
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
        scheduleDrinkReminder(drinkReminder)
        
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.first()).isEqualTo(drinkReminder.copy(time = drinkReminder.time.truncateToMinutes()))
    }
}