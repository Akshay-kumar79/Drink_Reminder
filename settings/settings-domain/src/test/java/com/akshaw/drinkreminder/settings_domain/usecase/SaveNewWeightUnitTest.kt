package com.akshaw.drinkreminder.settings_domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWeightByUnit
import com.akshaw.drinkreminder.core.util.SWWException
import com.akshaw.drinkreminder.core.util.SameWeightUnitException
import com.akshaw.drinkreminder.coretest.FakePreference
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SaveNewWeightUnitTest {
    
    private lateinit var preference: FakePreference
    private lateinit var changeWeightByUnit: ChangeWeightByUnit
    private lateinit var saveNewWeightUnit: SaveNewWeightUnit
    
    @BeforeEach
    fun setUp() {
        preference = FakePreference()
        changeWeightByUnit = ChangeWeightByUnit()
        saveNewWeightUnit = SaveNewWeightUnit(preference, changeWeightByUnit)
    }
    
    @ParameterizedTest
    @CsvSource(
        "kg, lbs, 0.0",
        "kg, lbs, 10.0",
        "lbs, kg, 1.0",
        "lbs, kg, 100.0",
    )
    fun `save water unit with correct input and preference weight, return success`(
        currentWeightUnit: String,
        newWeightUnit: String,
        weight: Float
    ) = runBlocking {
        preference.saveWeight(weight)
        preference.saveWeightUnit(WeightUnit.fromString(currentWeightUnit)!!)
        
        val result = saveNewWeightUnit(WeightUnit.fromString(newWeightUnit)!!)
        
        assertThat(result.isSuccess).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "lbs",
        "kg"
    )
    fun `save same weight unit, return failure with SameWeightUnitException`(
        unitString: String
    ) = runBlocking {
        val unit = WeightUnit.fromString(unitString)!!
        
        preference.saveWeightUnit(unit)
        val result = saveNewWeightUnit(unit)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(SameWeightUnitException().message)
    }
    
    @ParameterizedTest
    @CsvSource(
        "kg, lbs",
        "lbs, kg"
    )
    fun `save weight unit when preference has negative weight, return failure with SWWException`(
        currentWeightUnit: String,
        newWeightUnit: String
    ) = runBlocking {
        preference.saveWeight(-1.0f)
        preference.saveWeightUnit(WeightUnit.fromString(currentWeightUnit)!!)
        
        val result = saveNewWeightUnit(WeightUnit.fromString(newWeightUnit)!!)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(SWWException().message)
    }
    
}