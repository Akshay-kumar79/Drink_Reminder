package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import javax.inject.Inject

/**
 *  Convert weight with new weight unit
 */
// @Inject is used to make ChangeWeightByUnit available to DaggerHilt to be able to inject
class ChangeWeightByUnit @Inject constructor() {
    
    /**
     *  @param weight the weight that need to be converted
     *  @param currentWeightUnit the weight unit of the weight
     *  @param newWeightUnit the weight unit to which weight need to be converted
     *
     *  @return new weight
     */
    operator fun invoke(weight: Float, currentWeightUnit: WeightUnit, newWeightUnit: WeightUnit): Float {
        val newWeight = when (currentWeightUnit) {
            WeightUnit.KG -> {
                when (newWeightUnit) {
                    WeightUnit.KG -> weight
                    WeightUnit.LBS -> (weight * Constants.KG_TO_LBS).toFloat()
                }
            }
            
            WeightUnit.LBS -> {
                when (newWeightUnit) {
                    WeightUnit.KG -> (weight * Constants.LBS_TO_KG).toFloat()
                    WeightUnit.LBS -> weight
                }
            }
        }
        
        return newWeight
    }
    
}