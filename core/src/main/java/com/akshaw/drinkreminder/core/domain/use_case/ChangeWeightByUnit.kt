package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import javax.inject.Inject

/**
 *  Convert weight with new weight unit
 */
class ChangeWeightByUnit @Inject constructor() {
    
    /**
     *  @param weight the weight that need to be converted
     *  @param currentWeightUnit the weight unit of the weight
     *  @param newWeightUnit the weight unit to which weight need to be converted
     *
     *  @return
     *  -> [Result.failure] if [weight] is negative
     *
     *  -> [Result.success]
     *  - with [weight], if [currentWeightUnit] is same as [newWeightUnit]
     *  - with new weight, if everything is ok
     */
    @Suppress("KotlinConstantConditions")
    operator fun invoke(weight: Float, currentWeightUnit: WeightUnit, newWeightUnit: WeightUnit): Result<Float> {
        val newWeight = if (weight < 0.0)
            return Result.failure(Exception("Negative weight"))
        else if (currentWeightUnit == newWeightUnit) {
            return Result.success(weight)
        } else when (currentWeightUnit) {
            WeightUnit.KG -> when (newWeightUnit) {
                WeightUnit.KG -> weight
                WeightUnit.LBS -> (weight * Constants.KG_TO_LBS).toFloat()
            }
            
            
            WeightUnit.LBS -> when (newWeightUnit) {
                WeightUnit.KG -> (weight * Constants.LBS_TO_KG).toFloat()
                WeightUnit.LBS -> weight
            }
        }
        
        return Result.success(newWeight)
    }
    
}