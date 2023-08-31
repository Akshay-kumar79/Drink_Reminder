package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import javax.inject.Inject

/**
 *  Convert water quantity with new water unit
 */
// @Inject is used to make ChangeWaterQuantityByUnit available to DaggerHilt to be able to inject
class ChangeWaterQuantityByUnit @Inject constructor() {
    
    /**
     *  @param waterQuantity the quantity that need to be converted
     *  @param currentWaterUnit the water unit of the currentQuantity
     *  @param newWaterUnit the water unit to which currentQuantity need to be converted
     *
     *  @return [Result.failure] if newWaterUnit type is [WaterUnit.Invalid]
     *
     *  [Result.success] with new water quantity
     */
    operator fun invoke(waterQuantity: Double, currentWaterUnit: WaterUnit, newWaterUnit: WaterUnit): Result<Double> {
        val newWaterQuantity = when (currentWaterUnit) {
            WaterUnit.ML -> {
                when (newWaterUnit) {
                    WaterUnit.ML -> waterQuantity
                    WaterUnit.FL_OZ -> waterQuantity * Constants.ML_TO_FLOZ
                    WaterUnit.Invalid -> return Result.failure(Exception("Something went wrong"))
                }
            }
            
            WaterUnit.FL_OZ -> {
                when (newWaterUnit) {
                    WaterUnit.ML -> waterQuantity * Constants.FLOZ_TO_ML
                    WaterUnit.FL_OZ -> waterQuantity
                    WaterUnit.Invalid -> return Result.failure(Exception("Something went wrong"))
                }
            }
            
            WaterUnit.Invalid -> {
                when (newWaterUnit) {
                    WaterUnit.ML -> waterQuantity
                    WaterUnit.FL_OZ -> waterQuantity
                    WaterUnit.Invalid -> return Result.failure(Exception("Something went wrong"))
                }
            }
        }
        
        return Result.success(newWaterQuantity)
    }
    
}