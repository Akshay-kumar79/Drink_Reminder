package com.akshaw.drinkreminder.settings_domain.usecase

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWeightByUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *  Save new weight unit along with new converted weight in preference
 */
class SaveNewWeightUnit @Inject constructor(
    private val preferences: Preferences,
    private val changeWeightUnit: ChangeWeightByUnit
) {
    
    suspend operator fun invoke(newWeightUnit: WeightUnit) {
        
        val currentWeightUnit = preferences.getWeightUnit().first()
        if (currentWeightUnit == newWeightUnit) {
            return
        }
        
        val currentWeight = preferences.getWeight().first()
        
        val newWeight = changeWeightUnit(currentWeight, currentWeightUnit, newWeightUnit)
        
        preferences.saveWeight(newWeight)
        preferences.saveWeightUnit(newWeightUnit)
    }
    
}