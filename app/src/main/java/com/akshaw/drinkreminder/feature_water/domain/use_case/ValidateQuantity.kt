package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.core.util.WaterUnit
import javax.inject.Inject

/**
 *  returns,
 *
 *  Result.Success with string containing only digit with size 3, if current water unit is ML
 *  Result.Success with string containing only digit with size 2, if current water unit is FL_OZ
 *
 *  Result.Failure,
 *      -> if filtered digit string length greater than 3 and current water unit is ML
 *      -> if filtered digit string length greater than 2 and current water unit is FL_OZ
 */
class ValidateQuantity @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) {
    operator fun invoke(amount: String): Result<String> {

        val maxDigits = when (preferences.loadWaterUnit()) {
            WaterUnit.ML -> 3
            WaterUnit.FL_OZ -> 2
            WaterUnit.INVALID -> 0
        }
        
        val filteredAmount = filterOutDigits(amount)

        return if (filteredAmount.length <= maxDigits)
            Result.success(filteredAmount)
        else
            Result.failure(Exception("Cannot exceed maximum limit"))

    }

}