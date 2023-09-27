package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GetRecommendedDailyWaterIntakeTest {
    
    private lateinit var getRecommendedDailyWaterIntake: GetRecommendedDailyWaterIntake
    private lateinit var changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
    private lateinit var changeWeightByUnit: ChangeWeightByUnit
    
    @BeforeEach
    fun setUp() {
        changeWaterQuantityByUnit = ChangeWaterQuantityByUnit()
        changeWeightByUnit = ChangeWeightByUnit()
        getRecommendedDailyWaterIntake = GetRecommendedDailyWaterIntake(changeWaterQuantityByUnit, changeWeightByUnit)
    }
    
    companion object {
        private const val KG_TO_LBS = 2.20462262185
    }
    
    @ParameterizedTest
    @CsvSource(
        "12, 42, kg, Female, ml",
        "-1, 42, kg, Male, ml",
        "126, 42, kg, Male, ml",
        "${Int.MAX_VALUE}, 42, kg, Female, ml",
        "0, 42, kg, Female, ml",
        "14, 10, kg, Male, ml",
        "14, -1, kg, Female, ml",
        "14, 351, kg, Male, ml",
        "14, ${Int.MAX_VALUE}, kg, Female, ml",
        "14, 0, kg, Female, ml",
        "12, 10, kg, Female, ml",
        "-1, -1, kg, Female, ml",
        "126, 351, kg, Male, ml",
        "${Int.MAX_VALUE}, ${Int.MAX_VALUE}, kg, Female, ml",
        "0, 0, lbs, Male, ml",
        "12, ${42 * KG_TO_LBS}, lbs, Male, ml",
        "-1, ${42 * KG_TO_LBS}, lbs, Male, ml",
        "126, ${42 * KG_TO_LBS}, lbs, Female, ml",
        "${Int.MAX_VALUE}, ${42 * KG_TO_LBS}, lbs, Male, ml",
        "0, ${42 * KG_TO_LBS}, lbs, Male, ml",
        "14, ${10 * KG_TO_LBS}, lbs, Male, ml",
        "14, -1, lbs, Male, ml",
        "14, ${351 * KG_TO_LBS}, lbs, Female, ml",
        "14, ${Int.MAX_VALUE}, lbs, Female, ml",
        "14, 0, lbs, Male, ml",
        "12, ${10 * KG_TO_LBS}, lbs, Male, ml",
        "-1, -1, lbs, Female, ml",
        "126, ${351 * KG_TO_LBS}, lbs, Male, ml",
        "${Int.MAX_VALUE}, ${Int.MAX_VALUE}, lbs, Male, ml",
        "0, 0, lbs, Male, ml",
    
        "12, 42, kg, Female, fl oz",
        "-1, 42, kg, Male, fl oz",
        "126, 42, kg, Male, fl oz",
        "${Int.MAX_VALUE}, 42, kg, Female, fl oz",
        "0, 42, kg, Female, fl oz",
        "14, 10, kg, Male, fl oz",
        "14, -1, kg, Female, fl oz",
        "14, 351, kg, Male, fl oz",
        "14, ${Int.MAX_VALUE}, kg, Female, fl oz",
        "14, 0, kg, Female, fl oz",
        "12, 10, kg, Female, fl oz",
        "-1, -1, kg, Female, fl oz",
        "126, 351, kg, Male, fl oz",
        "${Int.MAX_VALUE}, ${Int.MAX_VALUE}, kg, Female, fl oz",
        "0, 0, lbs, Male, fl oz",
        "12, ${42 * KG_TO_LBS}, lbs, Male, fl oz",
        "-1, ${42 * KG_TO_LBS}, lbs, Male, fl oz",
        "126, ${42 * KG_TO_LBS}, lbs, Female, fl oz",
        "${Int.MAX_VALUE}, ${42 * KG_TO_LBS}, lbs, Male, fl oz",
        "0, ${42 * KG_TO_LBS}, lbs, Male, fl oz",
        "14, ${10 * KG_TO_LBS}, lbs, Male, fl oz",
        "14, -1, lbs, Male, fl oz",
        "14, ${351 * KG_TO_LBS}, lbs, Female, fl oz",
        "14, ${Int.MAX_VALUE}, lbs, Female, fl oz",
        "14, 0, lbs, Male, fl oz",
        "12, ${10 * KG_TO_LBS}, lbs, Male, fl oz",
        "-1, -1, lbs, Female, fl oz",
        "126, ${351 * KG_TO_LBS}, lbs, Male, fl oz",
        "${Int.MAX_VALUE}, ${Int.MAX_VALUE}, lbs, Male, fl oz",
        "0, 0, lbs, Male, ml",
    )
    // age in (13..125) and weight in (11..350)kg
    fun `invalid age or weight, returns failure`(
        currentAge: Int,
        currentWeight: Float,
        currentWeightUnit: String,
        gender: String,
        currentWaterUnit: String
    ) {
        val result = getRecommendedDailyWaterIntake(
            currentAge,
            currentWeight,
            WeightUnit.fromString(currentWeightUnit)!!,
            Gender.fromString(gender)!!,
            WaterUnit.fromString(currentWaterUnit)!!
        )
        
        assertThat(result.isFailure).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "13, 42, kg, Male, ml, 1940",
        "125, 42, kg, Male, ml, 3500",
        "100, 42, kg, Male, ml, 3500",
        "13, 42, kg, Female, ml, 1940",
        "125, 42, kg, Female, ml, 2700",
        "100, 42, kg, Female, ml, 2700",
        "32, 11, kg, Male, ml, 3500",
        "32, 350, kg, Male, ml, 3500",
        "32, 243, kg, Male, ml, 3500",
        "32, 11, kg, Female, ml, 2700",
        "32, 350, kg, Female, ml, 2700",
        "32, 243, kg, Female, ml, 2700",
        "13, 11, kg, Male, ml, 1050",
        "125, 350, kg, Male, ml, 3500",
        "100, 243, kg, Male, ml, 3500",
        "13, 11, kg, Female, ml, 1050",
        "125, 350, kg, Female, ml, 2700",
        "100, 243, kg, Female, ml, 2700",
        "13, ${42 * KG_TO_LBS}, lbs, Male, ml, 1940",
        "125, ${42 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "100, ${42 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "13, ${42 * KG_TO_LBS}, lbs, Female, ml, 1940",
        "125, ${42 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "100, ${42 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "32, ${11 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "32, ${350 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "32, ${243 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "32, ${11 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "32, ${350 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "32, ${243 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "13, ${11 * KG_TO_LBS}, lbs, Male, ml, 1050",
        "125, ${350 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "100, ${243 * KG_TO_LBS}, lbs, Male, ml, 3500",
        "13, ${11 * KG_TO_LBS}, lbs, Female, ml, 1050",
        "125, ${350 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "100, ${243 * KG_TO_LBS}, lbs, Female, ml, 2700",
        "13, 42, kg, Male, fl oz, 65",
        "125, 42, kg, Male, fl oz, 118",
        "100, 42, kg, Male, fl oz, 118",
        "13, 42, kg, Female, fl oz, 65",
        "125, 42, kg, Female, fl oz, 91",
        "100, 42, kg, Female, fl oz, 91",
        "32, 11, kg, Male, fl oz, 118",
        "32, 350, kg, Male, fl oz, 118",
        "32, 243, kg, Male, fl oz, 118",
        "32, 11, kg, Female, fl oz, 91",
        "32, 350, kg, Female, fl oz, 91",
        "32, 243, kg, Female, fl oz, 91",
        "13, 11, kg, Male, fl oz, 35",
        "125, 350, kg, Male, fl oz, 118",
        "100, 243, kg, Male, fl oz, 118",
        "13, 11, kg, Female, fl oz, 35",
        "125, 350, kg, Female, fl oz, 91",
        "100, 243, kg, Female, fl oz, 91",
        "13, ${42 * KG_TO_LBS}, lbs, Male, fl oz, 65",
        "125, ${42 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "100, ${42 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "13, ${42 * KG_TO_LBS}, lbs, Female, fl oz, 65",
        "125, ${42 * KG_TO_LBS}, lbs, Female, fl oz, 91",
        "100, ${42 * KG_TO_LBS}, lbs, Female, fl oz, 91",
        "32, ${11 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "32, ${350 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "32, ${243 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "32, ${11 * KG_TO_LBS}, lbs, Female, fl oz, 91",
        "32, ${350 * KG_TO_LBS}, lbs, Female, fl oz, 91",
        "32, ${243 * KG_TO_LBS}, lbs, Female, fl oz, 91",
        "13, ${11 * KG_TO_LBS}, lbs, Male, fl oz, 35",
        "125, ${350 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "100, ${243 * KG_TO_LBS}, lbs, Male, fl oz, 118",
        "13, ${11 * KG_TO_LBS}, lbs, Female, fl oz, 35",
        "125, ${350 * KG_TO_LBS}, lbs, Female, fl oz, 91",
        "100, ${243 * KG_TO_LBS}, lbs, Female, fl oz, 91",
    )
    // age in (13..125) and weight in (11..350)kg
    fun `valid age and weight, returns success with expected output`(
        currentAge: Int,
        currentWeight: Float,
        currentWeightUnit: String,
        gender: String,
        currentWaterUnit: String,
        expectedOutput: Int
    ) {
        val result = getRecommendedDailyWaterIntake(
            currentAge,
            currentWeight,
            WeightUnit.fromString(currentWeightUnit)!!,
            Gender.fromString(gender)!!,
            WaterUnit.fromString(currentWaterUnit)!!
        )
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedOutput)
    }
    
    
}