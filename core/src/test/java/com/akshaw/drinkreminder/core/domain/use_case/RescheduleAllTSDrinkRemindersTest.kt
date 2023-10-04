package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import com.akshaw.drinkreminder.core.util.truncateToMinutes
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import com.akshaw.drinkreminder.coretest.repository.FakeReminderScheduler
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.stream.Stream

class RescheduleAllTSDrinkRemindersTest {
    
    private lateinit var rescheduleAllTSDrinkReminders: RescheduleAllTSDrinkReminders
    private lateinit var scheduleDrinkReminder: ScheduleDrinkReminder
    private lateinit var reminderRepository: FakeReminderRepository
    private lateinit var reminderScheduler: FakeReminderScheduler
    
    @BeforeEach
    fun setUp() {
        reminderScheduler = FakeReminderScheduler()
        scheduleDrinkReminder = ScheduleDrinkReminder(reminderScheduler)
        reminderRepository = FakeReminderRepository()
        rescheduleAllTSDrinkReminders = RescheduleAllTSDrinkReminders(scheduleDrinkReminder, reminderRepository)
    }
    
    @ParameterizedTest
    @MethodSource("rescheduleArgs")
    fun `reschedule alarms, schedule the reminders`(
        drinkReminders: List<DrinkReminder>
    ) = runBlocking {
        drinkReminders.forEach {
            reminderRepository.drinkReminders.add(it)
        }
        
        rescheduleAllTSDrinkReminders()
        
        assertThat(reminderScheduler.reminders.size).isEqualTo(drinkReminders.size)
        drinkReminders.forEach {
            assertThat(reminderScheduler.reminders.contains(it.copy(time = it.time.truncateToMinutes()))).isTrue()
        }
    }
    
    @ParameterizedTest
    @MethodSource("rescheduleFailArgs")
    fun `reschedule alarms failure test with noPermission or exactAlarm exceptions`(
        drinkReminders: List<DrinkReminder>,
        exception: Exception
    ) = runBlocking {
        drinkReminders.forEach {
            reminderRepository.drinkReminders.add(it)
        }

        reminderScheduler.scheduleException = exception

        rescheduleAllTSDrinkReminders()

        assertThat(reminderScheduler.reminders.size).isEqualTo(0)
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(drinkReminders.size)
        reminderRepository.drinkReminders.forEach {
            assertThat(it.isReminderOn).isFalse()
        }
    }
    
    companion object {
        @JvmStatic
        fun rescheduleArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(listOf(
                DrinkReminder(0, LocalTime.now(), true, DayOfWeek.values().toList()),
                DrinkReminder(1, LocalTime.now(), false, DayOfWeek.values().toList()),
                DrinkReminder(2, LocalTime.now(), true, DayOfWeek.values().toList()),
                DrinkReminder(3, LocalTime.now(), true, DayOfWeek.values().toList()),
            )),
            Arguments.arguments(listOf<DrinkReminder>())
        )
    
        @JvmStatic
        fun rescheduleFailArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(listOf(
                DrinkReminder(1, LocalTime.now(), false, DayOfWeek.values().toList()),
                DrinkReminder(2, LocalTime.now(), false, DayOfWeek.values().toList()),
                DrinkReminder(3, LocalTime.now(), true, DayOfWeek.values().toList()),
                DrinkReminder(4, LocalTime.now(), true, DayOfWeek.values().toList()),
            ), NoNotificationPermissionException()),
            Arguments.arguments(listOf<DrinkReminder>(), NoNotificationPermissionException()),
            Arguments.arguments(listOf(
                DrinkReminder(1, LocalTime.now(), true, DayOfWeek.values().toList()),
                DrinkReminder(2, LocalTime.now(), true, DayOfWeek.values().toList()),
                DrinkReminder(3, LocalTime.now(), false, DayOfWeek.values().toList()),
                DrinkReminder(4, LocalTime.now(), true, DayOfWeek.values().toList()),
            ), NoExactAlarmPermissionException()),
            Arguments.arguments(listOf<DrinkReminder>(), NoExactAlarmPermissionException()),
        )
    }
}