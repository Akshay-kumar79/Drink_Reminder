package com.akshaw.drinkreminder.onboarding_domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.coretest.repository.FakeReminderRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalTime
import java.util.stream.Stream

class SaveInitialRemindersTest {
    
    private lateinit var saveInitialReminders: SaveInitialReminders
    private lateinit var preference: FakePreference
    private lateinit var reminderRepository: FakeReminderRepository
    private lateinit var upsertDrinkReminder: UpsertDrinkReminder
    
    @BeforeEach
    fun setUp() {
        preference = FakePreference()
        reminderRepository = FakeReminderRepository()
        upsertDrinkReminder = UpsertDrinkReminder(reminderRepository)
        saveInitialReminders = SaveInitialReminders(preference, upsertDrinkReminder)
    }
    
    @ParameterizedTest
    @MethodSource("onboardingNotCompArgs")
    fun `saveInitialReminders when onboarding is not completed test`(
        wakeupTime: LocalTime,
        bedTime: LocalTime,
        expectedReminders: Int,
        expectedFirstReminderTime: LocalTime
    ) = runBlocking {
        preference.saveIsOnboardingCompleted(false)
        preference.saveWakeupTime(wakeupTime)
        preference.saveBedTime(bedTime)
        
        reminderRepository.drinkReminders.clear()
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(0)
        saveInitialReminders()
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(expectedReminders)
        getReminders(expectedFirstReminderTime, expectedReminders)
            .forEachIndexed { index, time ->
                assertThat(reminderRepository.drinkReminders[index].time).isEqualTo(time)
            }
    }
    
    @ParameterizedTest
    @MethodSource("onboardingCompArgs")
    fun `saveInitialReminders when onboarding is completed test`(
        wakeupTime: LocalTime,
        bedTime: LocalTime,
    ) = runBlocking {
        preference.saveIsOnboardingCompleted(true)
        preference.saveWakeupTime(wakeupTime)
        preference.saveBedTime(bedTime)
        
        reminderRepository.drinkReminders.clear()
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(0)
        saveInitialReminders()
        
        assertThat(reminderRepository.drinkReminders.size).isEqualTo(0)
    }
    
    
    companion object {
        
        @JvmStatic
        private fun onboardingNotCompArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(LocalTime.of(7, 0), LocalTime.of(21, 0), 10, LocalTime.of(7, 15)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(7, 0), 7, LocalTime.of(21, 30)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(21, 0), 1, LocalTime.of(21, 0)),
            Arguments.arguments(LocalTime.of(20, 59), LocalTime.of(21, 0), 1, LocalTime.of(20, 59)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(20, 59), 16, LocalTime.of(21, 44)),
            Arguments.arguments(LocalTime.of(0, 0), LocalTime.of(23, 59), 16, LocalTime.of(0, 44)),
            Arguments.arguments(LocalTime.of(23, 59), LocalTime.of(0, 0), 1, LocalTime.of(23, 59)),
            Arguments.arguments(LocalTime.of(13, 0), LocalTime.of(13, 0), 1, LocalTime.of(13, 0)),
            Arguments.arguments(LocalTime.of(9, 0), LocalTime.of(21, 0), 8, LocalTime.of(9, 45)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(9, 0), 8, LocalTime.of(21, 45))
        )
        
        @JvmStatic
        private fun onboardingCompArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(LocalTime.of(7, 0), LocalTime.of(21, 0)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(7, 0)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(21, 0)),
            Arguments.arguments(LocalTime.of(20, 59), LocalTime.of(21, 0)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(20, 59)),
            Arguments.arguments(LocalTime.of(0, 0), LocalTime.of(23, 59)),
            Arguments.arguments(LocalTime.of(23, 59), LocalTime.of(0, 0)),
            Arguments.arguments(LocalTime.of(13, 0), LocalTime.of(13, 0)),
            Arguments.arguments(LocalTime.of(9, 0), LocalTime.of(21, 0)),
            Arguments.arguments(LocalTime.of(21, 0), LocalTime.of(9, 0))
        )
        
    }
    
    /**
     *  @return list of [LocalTime] starting from [firstReminderTime] up-to size of [numberOfReminders]
     *  with a gap of 90 minutes
     */
    private fun getReminders(firstReminderTime: LocalTime, numberOfReminders: Int): List<LocalTime> {
        var tempTime = firstReminderTime
        val times = mutableListOf<LocalTime>()
        for (i in 1..numberOfReminders) {
            times.add(tempTime)
            tempTime = tempTime.plusMinutes(90)
        }
        
        return times
    }
}