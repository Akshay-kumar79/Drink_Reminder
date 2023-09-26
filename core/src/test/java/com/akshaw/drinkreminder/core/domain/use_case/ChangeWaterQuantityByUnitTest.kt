package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ChangeWaterQuantityByUnitTest {
    
    private lateinit var changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
    
    companion object {
        private const val ML_TO_FLOZ = 0.033814022701843
        private const val FLOZ_TO_ML = 29.5735295625
        
        private const val NEGATIVE_QUANTITY = "Negative quantity"
    }
    
    @BeforeEach
    fun setUp() {
        changeWaterQuantityByUnit = ChangeWaterQuantityByUnit()
    }
    
    @ParameterizedTest
    @CsvSource(
        "23.0, ml, fl oz, ${23.0 * ML_TO_FLOZ}",
        "0.0, ml, fl oz, ${0.0 * ML_TO_FLOZ}",
        "1.0, ml, fl oz, ${1.0 * ML_TO_FLOZ}",
        "${Double.MAX_VALUE}, ml, fl oz, ${Double.MAX_VALUE * ML_TO_FLOZ}",
        "10000000000000000000000000000000.0, ml, fl oz, ${10000000000000000000000000000000.0 * ML_TO_FLOZ}",
    )
    fun `change water unit ml to floz, returns success with expected value`(
        quantity: Double,
        currentWaterUnit: String,
        newWaterUnit: String,
        expectedOutput: Double
    ) {
        val result = changeWaterQuantityByUnit(
            quantity,
            WaterUnit.fromString(currentWaterUnit)!!,
            WaterUnit.fromString(newWaterUnit)!!
        )
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedOutput)
    }
    
    @ParameterizedTest
    @CsvSource(
        "23.0, fl oz, ml, ${23.0 * FLOZ_TO_ML}",
        "0.0, fl oz, ml, ${0.0 * FLOZ_TO_ML}",
        "1.0, fl oz, ml, ${1.0 * FLOZ_TO_ML}",
        "${Double.MAX_VALUE}, fl oz, ml, ${Double.MAX_VALUE * FLOZ_TO_ML}",
        "10000000000000000000000000000000.0, fl oz, ml, ${10000000000000000000000000000000.0 * FLOZ_TO_ML}",
    )
    fun `change water unit floz to ml, returns success with expected value`(
        quantity: Double,
        currentWaterUnit: String,
        newWaterUnit: String,
        expectedOutput: Double
    ) {
        val result = changeWaterQuantityByUnit(
            quantity,
            WaterUnit.fromString(currentWaterUnit)!!,
            WaterUnit.fromString(newWaterUnit)!!
        )
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedOutput)
    }
    
    @ParameterizedTest
    @CsvSource(
        "23.0, ml, ml",
        "0.0, ml, ml",
        "1.0, fl oz, fl oz",
        "${Double.MAX_VALUE}, fl oz, fl oz",
        "10000000000000000000000000000000.0, fl oz, fl oz",
    )
    fun `change water unit to same as it is, returns success with same amount`(
        quantity: Double,
        currentWaterUnit: String,
        newWaterUnit: String,
    ) {
        val result = changeWaterQuantityByUnit(
            quantity,
            WaterUnit.fromString(currentWaterUnit)!!,
            WaterUnit.fromString(newWaterUnit)!!
        )
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(quantity)
    }
    
    @ParameterizedTest
    @CsvSource(
        "-23.0, ml, fl oz",
        "-1.0, fl oz, ml",
        "${Int.MIN_VALUE}, fl oz, ml",
        "-10000000000000000000000000000000.0, fl oz, ml",
    )
    fun `change water unit with negative quantity, returns failure`(
        quantity: Double,
        currentWaterUnit: String,
        newWaterUnit: String,
    ) {
        val result = changeWaterQuantityByUnit(
            quantity,
            WaterUnit.fromString(currentWaterUnit)!!,
            WaterUnit.fromString(newWaterUnit)!!
        )
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(NEGATIVE_QUANTITY)
    }
    
}