package com.akshaw.drinkreminder.settings_domain.usecase

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWaterQuantityByUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.util.SWWException
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *  Save new water unit along with new converted daily water intake goal in preference
 */
class SaveNewWaterUnit @Inject constructor(
    private val preferences: Preferences,
    private val changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
) {
    
    suspend operator fun invoke(newWaterUnit: WaterUnit): Result<Unit> {
        
        val currentWaterUnit = preferences.getWaterUnit().first()
        if (currentWaterUnit == newWaterUnit) {
            return Result.failure(Exception("Current water unit is same"))
        }
        
        val currentDailyWaterIntake = preferences.getDailyWaterIntakeGoal().first()
        
        val newDailyWaterIntake = changeWaterQuantityByUnit(currentDailyWaterIntake, currentWaterUnit, newWaterUnit).getOrElse {
            return Result.failure(SWWException())
        }
        
        preferences.saveDailyWaterIntakeGoal(newDailyWaterIntake)
        preferences.saveWaterUnit(newWaterUnit)
        
        return Result.success(Unit)
    }
    
}