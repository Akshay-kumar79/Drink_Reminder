package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ChangeWeightByUnitTest {
    
    private lateinit var changeWeightByUnit: ChangeWeightByUnit
    
    companion object {
        private const val KG_TO_LBS = 2.20462262185
        private const val LBS_TO_KG = 0.45359237
        
        private const val NEGATIVE_WEIGHT = "Negative weight"
    }
    
    @BeforeEach
    fun setUp() {
        changeWeightByUnit = ChangeWeightByUnit()
    }
    
    @ParameterizedTest
    @CsvSource(
        "23.0, kg, lbs, ${23.0 * KG_TO_LBS}",
        "0.0, kg, lbs, ${0.0 * KG_TO_LBS}",
        "1.0, kg, lbs, ${1.0 * KG_TO_LBS}",
        "${Float.MAX_VALUE}, kg, lbs, ${Float.MAX_VALUE * KG_TO_LBS}",
        "10000000000000000000000000000000.0, kg, lbs, ${10000000000000000000000000000000.0 * KG_TO_LBS}",
    )
    fun `change weight unit kg to lbs, returns success with expected value`(
        weight: Float,
        currentWeightUnit: String,
        newWeightUnit: String,
        expectedOutput: Float
    ) {
        val result = changeWeightByUnit(
            weight,
            WeightUnit.fromString(currentWeightUnit)!!,
            WeightUnit.fromString(newWeightUnit)!!
        )
    
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedOutput)
    }
    
    @ParameterizedTest
    @CsvSource(
        "23.0, lbs, kg, ${23.0 * LBS_TO_KG}",
        "0.0, lbs, kg, ${0.0 * LBS_TO_KG}",
        "1.0, lbs, kg, ${1.0 * LBS_TO_KG}",
        "${Float.MAX_VALUE}, lbs, kg, ${Float.MAX_VALUE * LBS_TO_KG}",
        "100000000000000000000000000000.0, lbs, kg, ${100000000000000000000000000000.0 * LBS_TO_KG}",
    )
    fun `change weight unit lbs to kg, returns success with expected value`(
        weight: Float,
        currentWeightUnit: String,
        newWeightUnit: String,
        expectedOutput: Float
    ) {
        val result = changeWeightByUnit(
            weight,
            WeightUnit.fromString(currentWeightUnit)!!,
            WeightUnit.fromString(newWeightUnit)!!
        )
    
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedOutput)
    }
    
    @ParameterizedTest
    @CsvSource(
        "23.0, kg, kg",
        "0.0, kg, kg",
        "1.0, lbs, lbs",
        "${Float.MAX_VALUE}, lbs, lbs",
        "100000000000000000000000000000.0, lbs, lbs",
    )
    fun `change weight unit to same as it is, returns success with same weight`(
        weight: Float,
        currentWeightUnit: String,
        newWeightUnit: String,
    ) {
        val result = changeWeightByUnit(
            weight,
            WeightUnit.fromString(currentWeightUnit)!!,
            WeightUnit.fromString(newWeightUnit)!!
        )
    
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(weight)
    }
    
    @ParameterizedTest
    @CsvSource(
        "-23.0, kg, lbs",
        "-1.0, kg, lbs",
        "${Int.MIN_VALUE}, lbs, kg",
        "-100000000000000000000000000000.0, lbs, kg",
    )
    fun `change weight unit with negative weight, returns failure`(
        weight: Float,
        currentWeightUnit: String,
        newWeightUnit: String,
    ) {
        val result = changeWeightByUnit(
            weight,
            WeightUnit.fromString(currentWeightUnit)!!,
            WeightUnit.fromString(newWeightUnit)!!
        )
    
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(NEGATIVE_WEIGHT)
    }
}