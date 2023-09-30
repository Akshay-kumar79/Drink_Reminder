package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import javax.inject.Inject

/**
 *  Convert water quantity with new water unit
 */
class ChangeWaterQuantityByUnit @Inject constructor() {
    
    /**
     *  @param waterQuantity the quantity that need to be converted
     *  @param currentWaterUnit the water unit of the currentQuantity
     *  @param newWaterUnit the water unit to which currentQuantity need to be converted
     *
     *  @return
     *  -> [Result.failure] if [waterQuantity] is negative
     *
     *  -> [Result.success]
     *  - with [waterQuantity], if [currentWaterUnit] is same as [newWaterUnit]
     *  - with new water quantity, if everything is ok
     */
    @Suppress("KotlinConstantConditions")
    operator fun invoke(waterQuantity: Double, currentWaterUnit: WaterUnit, newWaterUnit: WaterUnit): Result<Double> {
        
        val newWaterQuantity = waterQuantity.let {
            if (it < 0.0)
                return Result.failure(Exception("Negative quantity"))
            else if (currentWaterUnit == newWaterUnit) {
                return Result.success(waterQuantity)
            } else {
                when (currentWaterUnit) {
                    WaterUnit.ML -> {
                        when (newWaterUnit) {
                            WaterUnit.ML -> waterQuantity
                            WaterUnit.FL_OZ -> waterQuantity * Constants.ML_TO_FLOZ
                        }
                    }
                    
                    WaterUnit.FL_OZ -> {
                        when (newWaterUnit) {
                            WaterUnit.ML -> waterQuantity * Constants.FLOZ_TO_ML
                            WaterUnit.FL_OZ -> waterQuantity
                        }
                    }
                }
            }
        }
        
        return Result.success(newWaterQuantity)
    }
    
}