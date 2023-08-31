package com.akshaw.drinkreminder.settings_domain.usecase

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWaterQuantityByUnit
import com.akshaw.drinkreminder.core.util.WaterUnit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *  Save new water unit along with new converted daily water intake goal in preference
 */
class SaveNewWaterUnit @Inject constructor(
    private val preferences: Preferences,
    private val changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
) {
    
    /**
     *  @return [Result.success] -> if current water unit from [Preferences] is same as newWaterUnit
     *  Or if newWaterUnit is saved successfully in preference with new converted dailyWaterIntakeGoal
     *
     *  [Result.failure] -> if failed to convert current daily water intake goal quantity to newWaterUnit quantity
     */
    suspend operator fun invoke(newWaterUnit: WaterUnit): Result<Unit> {
        
        val currentWaterUnit = preferences.getWaterUnit().first()
        if (currentWaterUnit == newWaterUnit) {
            return Result.success(Unit)
        }
        
        val currentDailyWaterIntake = preferences.getDailyWaterIntakeGoal().first()
        
        val newDailyWaterIntake = changeWaterQuantityByUnit(currentDailyWaterIntake, currentWaterUnit, newWaterUnit)
            .getOrElse {
                return Result.failure(Exception(it.message))
            }
        
        preferences.saveDailyWaterIntakeGoal(newDailyWaterIntake)
        preferences.saveWaterUnit(newWaterUnit)
        
        return Result.success(Unit)
        
    }
    
}