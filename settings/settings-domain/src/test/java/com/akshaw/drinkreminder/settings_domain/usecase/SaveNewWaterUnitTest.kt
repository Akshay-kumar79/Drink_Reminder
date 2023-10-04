package com.akshaw.drinkreminder.settings_domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWaterQuantityByUnit
import com.akshaw.drinkreminder.core.util.SWWException
import com.akshaw.drinkreminder.core.util.SameWaterUnitException
import com.akshaw.drinkreminder.coretest.FakePreference
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SaveNewWaterUnitTest {
    
    private lateinit var preference: FakePreference
    private lateinit var changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
    private lateinit var saveNewWaterUnit: SaveNewWaterUnit
    
    @BeforeEach
    fun setUp() {
        preference = FakePreference()
        changeWaterQuantityByUnit = ChangeWaterQuantityByUnit()
        saveNewWaterUnit = SaveNewWaterUnit(preference, changeWaterQuantityByUnit)
    }
    
    
    @ParameterizedTest
    @CsvSource(
        "ml, fl oz, 0.0",
        "ml, fl oz, 10.0",
        "fl oz, ml, 1.0",
        "fl oz, ml, 100.0",
    )
    fun `save water unit with correct input and preference dailyIntakeGoal, return success`(
        currentWaterUnit: String,
        newWaterUnit: String,
        dailyIntakeGoal: Double
    ) = runBlocking {
        preference.saveDailyWaterIntakeGoal(dailyIntakeGoal)
        preference.saveWaterUnit(WaterUnit.fromString(currentWaterUnit)!!)
        
        val result = saveNewWaterUnit(WaterUnit.fromString(newWaterUnit)!!)
        
        assertThat(result.isSuccess).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "fl oz",
        "ml"
    )
    fun `save same water unit, return failure with SameWaterUnitException`(
        unitString: String
    ) = runBlocking {
        val unit = WaterUnit.fromString(unitString)!!
        
        preference.saveWaterUnit(unit)
        val result = saveNewWaterUnit(unit)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(SameWaterUnitException().message)
    }
    
    @ParameterizedTest
    @CsvSource(
        "ml, fl oz",
        "fl oz, ml"
    )
    fun `save water unit when preference has negative dailyIntakeGoal, return failure with SWWException`(
        currentWaterUnit: String,
        newWaterUnit: String
    ) = runBlocking {
        preference.saveDailyWaterIntakeGoal(-1.0)
        preference.saveWaterUnit(WaterUnit.fromString(currentWaterUnit)!!)
        
        val result = saveNewWaterUnit(WaterUnit.fromString(newWaterUnit)!!)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(SWWException().message)
    }
    
}