package com.akshaw.drinkreminder.onboarding_domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWaterQuantityByUnit
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWeightByUnit
import com.akshaw.drinkreminder.core.domain.use_case.GetRecommendedDailyWaterIntake
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.coretest.FakePreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveInitialDailyIntakeGoalTest {
    
    private lateinit var saveInitialDailyIntakeGoal: SaveInitialDailyIntakeGoal
    private lateinit var changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
    private lateinit var changeWeightByUnit: ChangeWeightByUnit
    private lateinit var getRecommendedDailyWaterIntake: GetRecommendedDailyWaterIntake
    private lateinit var preference: FakePreference
    
    @BeforeEach
    fun setUp() {
        preference = FakePreference()
        changeWaterQuantityByUnit = ChangeWaterQuantityByUnit()
        changeWeightByUnit = ChangeWeightByUnit()
        getRecommendedDailyWaterIntake = GetRecommendedDailyWaterIntake(changeWaterQuantityByUnit, changeWeightByUnit)
        saveInitialDailyIntakeGoal = SaveInitialDailyIntakeGoal(preference, getRecommendedDailyWaterIntake)
    }
    
    @Test
    fun `save daily intake goal test when onboarding is not completed`() = runBlocking {
        preference.saveIsOnboardingCompleted(false)
        
        preference.saveDailyWaterIntakeGoal(0.0)
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(0.0)
        
        saveInitialDailyIntakeGoal()
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(recommendedDailyIntake(preference))
        
        preference.saveDailyWaterIntakeGoal(100.0)
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(100.0)
    
        saveInitialDailyIntakeGoal()
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(recommendedDailyIntake(preference))
    }
    
    @Test
    fun `save daily intake goal test when onboarding is completed`() = runBlocking {
        preference.saveIsOnboardingCompleted(true)
        
        preference.saveDailyWaterIntakeGoal(0.0)
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(0.0)
        
        saveInitialDailyIntakeGoal()
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(0.0)
        
        preference.saveDailyWaterIntakeGoal(100.0)
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(100.0)
    
        saveInitialDailyIntakeGoal()
        assertThat(preference.getDailyWaterIntakeGoal().first()).isEqualTo(100.0)
    }
    
    
    private suspend fun recommendedDailyIntake(preference: FakePreference) = getRecommendedDailyWaterIntake(
        preference.getAge().first(),
        preference.getWeight().first(),
        preference.getWeightUnit().first(),
        preference.getGender().first(),
        preference.getWaterUnit().first()
    ).getOrDefault(Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL).toDouble()
}