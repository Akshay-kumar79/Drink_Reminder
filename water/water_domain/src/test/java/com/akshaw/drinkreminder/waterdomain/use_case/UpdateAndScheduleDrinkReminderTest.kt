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

class UpdateAndScheduleDrinkReminderTest {
    
    private lateinit var updateAndScheduleDrinkReminder: UpdateAndScheduleDrinkReminder
    private lateinit var reminderScheduler: FakeReminderScheduler
    private lateinit var reminderRepository: FakeReminderRepository
    private lateinit var scheduleDrinkReminder: ScheduleDrinkReminder
    private lateinit var upsertDrinkReminder: UpsertDrinkReminder
    
    @BeforeEach
    fun setUp() {
        reminderScheduler = FakeReminderScheduler()
        scheduleDrinkReminder = ScheduleDrinkReminder(reminderScheduler)
        reminderRepository = FakeReminderRepository()
        upsertDrinkReminder = UpsertDrinkReminder(reminderRepository)
        updateAndScheduleDrinkReminder = UpdateAndScheduleDrinkReminder(scheduleDrinkReminder, upsertDrinkReminder)
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
        newHour: Int,
        newMinute: Int
    ) = runBlocking {
        
        val drinkReminder = DrinkReminder(
            id = 1,
            time = LocalTime.of(22, 22),
            isReminderOn = true,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        reminderScheduler.reminders.add(drinkReminder)
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.first()).isEqualTo(drinkReminder)
        
        val result = updateAndScheduleDrinkReminder(drinkReminder, newHour, newMinute, DayOfWeek.values().associateBy({ it.ordinal }, { true }))
        
        assertThat(result.isFailure).isTrue()
        
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
    fun `UpdateAndSchedule with correct input, returns success`(
        newHour: Int,
        newMinute: Int
    ) = runBlocking {
        
        val drinkReminder = DrinkReminder(
            id = 1,
            time = LocalTime.of(22, 22),
            isReminderOn = true,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        reminderScheduler.reminders.add(drinkReminder)
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.first()).isEqualTo(drinkReminder)
        
        val result = updateAndScheduleDrinkReminder(drinkReminder, newHour, newMinute, DayOfWeek.values().associateBy({ it.value }, { true }))
        
        assertThat(result.isSuccess).isTrue()
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder.copy(time = LocalTime.of(newHour, newMinute)))
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.first()).isEqualTo(drinkReminder.copy(time = LocalTime.of(newHour, newMinute)))
    }
    
    
    @ParameterizedTest
    @CsvSource(
        "12, 12",
        "00, 59",
        "23, 50",
    )
    fun `AddAndSchedule with correct input without required permissions, returns success and reminders saved as off state`(
        newHour: Int,
        newMinute: Int
    ) = runBlocking {
        
        reminderScheduler.scheduleException = NoNotificationPermissionException()
        
        val drinkReminder = DrinkReminder(
            id = 1,
            time = LocalTime.of(22, 22),
            isReminderOn = true,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        reminderScheduler.reminders.add(drinkReminder)
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.first()).isEqualTo(drinkReminder)
        
        val result = updateAndScheduleDrinkReminder(drinkReminder, newHour, newMinute, DayOfWeek.values().associateBy({ it.value }, { true }))
        
        assertThat(result.isSuccess).isTrue()
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(
            drinkReminder.copy(time = LocalTime.of(newHour, newMinute), isReminderOn = false)
        )
    }
    
}