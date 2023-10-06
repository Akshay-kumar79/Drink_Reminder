package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import com.akshaw.drinkreminder.coretest.repository.FakeReminderScheduler
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.DayOfWeek
import java.time.LocalTime

class AddAndScheduleDrinkReminderTest {
    
    private lateinit var addAndScheduleDrinkReminder: AddAndScheduleDrinkReminder
    private lateinit var reminderRepository: FakeReminderRepository
    private lateinit var upsertDrinkReminder: UpsertDrinkReminder
    private lateinit var reminderScheduler: FakeReminderScheduler
    private lateinit var scheduleDrinkReminder: ScheduleDrinkReminder
    
    @BeforeEach
    fun setUp() {
        reminderRepository = FakeReminderRepository()
        upsertDrinkReminder = UpsertDrinkReminder(reminderRepository)
        reminderScheduler = FakeReminderScheduler()
        scheduleDrinkReminder = ScheduleDrinkReminder(reminderScheduler)
        addAndScheduleDrinkReminder = AddAndScheduleDrinkReminder(upsertDrinkReminder, scheduleDrinkReminder)
    }
    
    @ParameterizedTest
    @CsvSource(
        "24, 12",
        "00, 60",
        "24, 60",
        "00, -12",
        "-22, 12",
        "-22, -12",
    )
    fun `no parsable time, returns failure`(
        hour: Int,
        minute: Int
    ) = runBlocking {
        val result = addAndScheduleDrinkReminder(hour, minute, DayOfWeek.values().associateBy({ it.ordinal }, { true }))
        
        assertThat(result.isFailure).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "12, 12",
        "00, 59",
        "23, 50",
    )
    fun `AddAndSchedule with correct input, returns success`(
        hour: Int,
        minute: Int
    ) = runBlocking {
        val result = addAndScheduleDrinkReminder(hour, minute, DayOfWeek.values().associateBy({ it.value }, { true }))
        
        val drinkReminder = DrinkReminder(1, LocalTime.of(hour, minute), true, DayOfWeek.values().toList())
        
        assertThat(result.isSuccess).isTrue()
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.first()).isEqualTo(drinkReminder)
    }
    
    @ParameterizedTest
    @CsvSource(
        "12, 12",
        "00, 59",
        "23, 50",
    )
    fun `AddAndSchedule with correct input without required permissions, returns success and reminders saved as off state`(
        hour: Int,
        minute: Int
    ) = runBlocking {

        reminderScheduler.scheduleException = NoNotificationPermissionException()

        val result = addAndScheduleDrinkReminder(hour, minute, DayOfWeek.values().associateBy({ it.value }, { true }))

        val drinkReminder = DrinkReminder(1, LocalTime.of(hour, minute), false, DayOfWeek.values().toList())

        assertThat(result.isSuccess).isTrue()
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
    }
    
}