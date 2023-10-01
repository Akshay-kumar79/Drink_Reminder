package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetRecommendedDailyWaterIntake
import com.akshaw.drinkreminder.core.util.Constants
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Saves Recommended Daily intake goal to preference if [Preferences.getIsOnboardingCompleted] is false.
 * Daily intake goal is calculated in [GetRecommendedDailyWaterIntake]
 */
class SaveInitialDailyIntakeGoal @Inject constructor(
    private val preferences: Preferences,
    private val getRecommendedDailyWaterIntake: GetRecommendedDailyWaterIntake
) {
    
    suspend operator fun invoke(){
    
        if (preferences.getIsOnboardingCompleted().first()) {
            return
        }
        
        val recommendedDailyWaterIntake = getRecommendedDailyWaterIntake(
            preferences.getAge().first(),
            preferences.getWeight().first(),
            preferences.getWeightUnit().first(),
            preferences.getGender().first(),
            preferences.getWaterUnit().first()
        ).getOrDefault(Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL).toDouble()
    
        preferences.saveDailyWaterIntakeGoal(recommendedDailyWaterIntake)
    }
    
}