package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import javax.inject.Inject
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 *  Get the recommended daily water intake as per your preferred [WaterUnit]
 */
class GetRecommendedDailyWaterIntake @Inject constructor(
    private val changeWaterQuantityByUnit: ChangeWaterQuantityByUnit,
    private val changeWeightByUnit: ChangeWeightByUnit
) {
    
    /**
     * @param currentAge current age of the user
     * @param currentWeight current weight of the user
     * @param currentWeightUnit current WeightUnit
     * @param gender You know it i know it
     * @param currentWaterUnit preferred [WaterUnit] for the output amount
     *
     * @return [Result.failure]
     * - if currentWeightUnit type is invalid,
     * - if current age is less than 13 year and more than [Constants.MAX_AGE] year,
     * - if currentWeight is less than 11 kg and more than [Constants.MAX_WEIGHT] kg
     * - if program reached the end of the function which shouldn't be the case
     *
     * [Result.success] if not failure then is surely a success with your
     * recommended daily water intake amount as per your currentWaterUnit
     */
    operator fun invoke(
        currentAge: Int,
        currentWeight: Float,
        currentWeightUnit: WeightUnit,
        gender: Gender,
        currentWaterUnit: WaterUnit
    ): Result<Int> {
        
        val currentWeightInKg = changeWeightByUnit(currentWeight, currentWeightUnit, WeightUnit.KG)
            .getOrElse {
                return Result.failure(it)
            }.roundToInt()
        
        val recommendedDailyIntakeInMl = when (currentAge) {
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
        
        changeWaterQuantityByUnit(recommendedDailyIntakeInMl.toDouble(), WaterUnit.ML, currentWaterUnit)
            .onSuccess {
                return Result.success(floor(it).toInt())
            }.onFailure {
                return Result.failure(Exception(it.message))
            }
        
        return Result.failure(Exception("Something went wrong"))
        
    }
    
}