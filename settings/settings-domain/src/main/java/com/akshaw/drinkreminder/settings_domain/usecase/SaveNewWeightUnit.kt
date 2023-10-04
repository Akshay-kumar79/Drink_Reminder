package com.akshaw.drinkreminder.settings_domain.usecase

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWeightByUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import com.akshaw.drinkreminder.core.util.SWWException
import com.akshaw.drinkreminder.core.util.SameWeightUnitException
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
     *  @param newWeightUnit [WeightUnit] that needs to be stored
     *
     *  @return
     *  -> [Result.success] if new unit is stored in preference successfully
     *
     *  -> [Result.failure]
     *  - with [SameWeightUnitException] if current weight is same as [newWeightUnit]
     *  - with [SWWException] if failed to convert unit of current weight
     */
    suspend operator fun invoke(newWeightUnit: WeightUnit): Result<Unit> {
        
        val currentWeightUnit = preferences.getWeightUnit().first()
        if (currentWeightUnit == newWeightUnit) {
            return Result.failure(SameWeightUnitException())
        }
        
        val currentWeight = preferences.getWeight().first()
        
        val newWeight = changeWeightUnit(currentWeight, currentWeightUnit, newWeightUnit).getOrElse {
            return Result.failure(SWWException())
        }
        
        preferences.saveWeight(newWeight)
        preferences.saveWeightUnit(newWeightUnit)
        
        return Result.success(Unit)
    }
    
}