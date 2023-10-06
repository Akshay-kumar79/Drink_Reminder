package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.coretest.repository.FakeReminderScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class CancelDrinkReminderTest {
    
    private lateinit var cancelDrinkReminder: CancelDrinkReminder
    private lateinit var reminderScheduler: FakeReminderScheduler
    
    @BeforeEach
    fun setUp() {
        reminderScheduler = FakeReminderScheduler()
        cancelDrinkReminder = CancelDrinkReminder(reminderScheduler)
    }
    
    
    @Test
    fun `cancel reminder test`() {
        val drinkReminder = DrinkReminder(
            time = LocalTime.now(),
            isReminderOn = true,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderScheduler.reminders.add(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        
        cancelDrinkReminder(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
    }
}