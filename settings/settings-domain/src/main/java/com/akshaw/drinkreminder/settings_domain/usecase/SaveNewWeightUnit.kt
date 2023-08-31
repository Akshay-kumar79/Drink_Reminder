package com.akshaw.drinkreminder.settings_domain.usecase

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWeightByUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *  Save new weight unit along with new converted weight in preference
 */
class SaveNewWeightUnit @Inject constructor(
    private val preferences: Preferences,
    private val changeWeightUnit: ChangeWeightByUnit
) {
    
    /**
     *  @return [Result.success] -> if current weight unit from [Preferences] is same as newWeightUnit
     *  Or if newWeightUnit is saved successfully in preference with new converted weight as well
     *
     *  [Result.failure] -> if failed to convert current weight to newWeight
     */
    suspend operator fun invoke(newWeightUnit: WeightUnit): Result<Unit> {
        val currentWeightUnit = preferences.getWeightUnit().first()
        if (currentWeightUnit == newWeightUnit) {
            return Result.success(Unit)
        }
        
        val currentWeight = preferences.getWeight().first()
        
        val newWeight = changeWeightUnit(currentWeight, currentWeightUnit, newWeightUnit)
            .getOrElse {
                return Result.failure(Exception(it.message))
            }
        
        preferences.saveWeight(newWeight)
        preferences.saveWeightUnit(newWeightUnit)
        
        return Result.success(Unit)
    }
    
}