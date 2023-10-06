package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import com.akshaw.drinkreminder.core.util.truncateToMinutes
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import com.akshaw.drinkreminder.coretest.repository.FakeReminderScheduler
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.DayOfWeek
import java.time.LocalTime

class SwitchDrinkReminderTest {
    
    private lateinit var switchDrinkReminder: SwitchDrinkReminder
    private lateinit var reminderRepository: FakeReminderRepository
    private lateinit var reminderScheduler: FakeReminderScheduler
    private lateinit var upsertDrinkReminder: UpsertDrinkReminder
    private lateinit var scheduleDrinkReminder: ScheduleDrinkReminder
    private lateinit var cancelDrinkReminder: CancelDrinkReminder
    
    @BeforeEach
    fun setUp() {
        reminderRepository = FakeReminderRepository()
        reminderScheduler = FakeReminderScheduler()
        upsertDrinkReminder = UpsertDrinkReminder(reminderRepository)
        scheduleDrinkReminder = ScheduleDrinkReminder(reminderScheduler)
        cancelDrinkReminder = CancelDrinkReminder(reminderScheduler)
        switchDrinkReminder = SwitchDrinkReminder(upsertDrinkReminder, scheduleDrinkReminder, cancelDrinkReminder)
    }
    
    @ParameterizedTest
    @CsvSource(
        "true",
        "false"
    )
    fun `turn off reminder, return success`(
        currentState: Boolean
    ) = runBlocking {
        val drinkReminder = DrinkReminder(
            id = 1,
            time = LocalTime.now().truncateToMinutes(),
            isReminderOn = currentState,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        reminderScheduler.reminders.add(drinkReminder)
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        
        val result = switchDrinkReminder(drinkReminder, false)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder.copy(isReminderOn = false))
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
    }
    
    @ParameterizedTest
    @CsvSource(
        "true",
        "false"
    )
    fun `turn on reminder, return success`(
        currentState: Boolean
    ) = runBlocking {
        val drinkReminder = DrinkReminder(
            id = 1,
            time = LocalTime.now().truncateToMinutes(),
            isReminderOn = currentState,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        
        val result = switchDrinkReminder(drinkReminder, true)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder.copy(isReminderOn = true))
        assertThat(reminderScheduler.reminders.size).isEqualTo(1)
    }
    
    @ParameterizedTest
    @CsvSource(
        "true, notification",
        "true, exactAlarm",
        "false, notification",
        "false, exactAlarm",
    )
    fun `turn on reminder without permission, return success`(
        currentState: Boolean,
        permissionException: String
    ) = runBlocking {
        
        reminderScheduler.scheduleException = if (permissionException == "notification")
            NoNotificationPermissionException()
        else
            NoExactAlarmPermissionException()
        
        val drinkReminder = DrinkReminder(
            id = 1,
            time = LocalTime.now().truncateToMinutes(),
            isReminderOn = currentState,
            activeDays = DayOfWeek.values().toList()
        )
        
        reminderRepository.drinkReminders.add(drinkReminder)
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder)
        
        val result = switchDrinkReminder(drinkReminder, true)
        
        assertThat(result.isFailure).isTrue()
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(1)
        assertThat(reminderRepository.drinkReminders.first()).isEqualTo(drinkReminder.copy(isReminderOn = false))
        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
    }
    
}