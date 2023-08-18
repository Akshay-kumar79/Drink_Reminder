package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WeightUnit
import kotlin.math.ceil

class GetRecommendedDailyWaterIntake {
    
    operator fun invoke(
        currentAge: Int,
        currentWeight: Float,
        currentWeightUnit: WeightUnit,
        gender: Gender
    ): Result<Int> {
        
        val currentWeightInKg: Int = when (currentWeightUnit) {
            WeightUnit.KG -> ceil(currentWeight).toInt()
            WeightUnit.LBS -> ceil(currentWeight * Constants.LBS_TO_KG).toInt()
            WeightUnit.Invalid -> return Result.failure(Exception("Invalid weight unit"))
        }
        
        val recommendedDailyIntake = when (currentAge) {
            in (13..18) -> {
                when (currentWeightInKg) {
                    in (11..20) -> {
                        // 1000 ml for first 10 kg + 50 ml/kg after first 10kg
                        (1000) + (50 * (currentWeightInKg - 10))
                    }
                    
                    in (21..Constants.MAX_WEIGHT) -> {
                        // 1500ml for first 20kg + 20 ml/kg after first 20kg
                        1500 + (20 * (currentWeightInKg - 20))
                    }
                    
                    else -> return Result.failure(Exception("Something went wrong"))
                }
            }
            
            in (19..Constants.MAX_AGE) -> {
                when (gender) {
                    Gender.Male -> 3500
                    Gender.Female -> 2700
                }
            }
            
            else -> return Result.failure(Exception("Something went wrong"))
        }
        
        return Result.success(recommendedDailyIntake)
    }
    
}