package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.core.util.WaterUnit
import javax.inject.Inject

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

        return if (amount.length <= maxDigits)
            Result.success(filterOutDigits(amount))
        else
            Result.failure(Exception("cannot exceed maximum limit"))

    }

}