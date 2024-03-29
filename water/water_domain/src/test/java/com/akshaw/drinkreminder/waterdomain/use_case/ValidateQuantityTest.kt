package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ValidateQuantityTest {
    
    private lateinit var preferences: Preferences
    private lateinit var filterOutDigits: FilterOutDigits
    private lateinit var validateQuantity: ValidateQuantity
    
    @BeforeEach
    fun setUp() {
        preferences = FakePreference()
        filterOutDigits = FilterOutDigits()
        validateQuantity = ValidateQuantity(
            filterOutDigits
        )
    }
    
    @Test
    fun `0 amount and water unit ML, returns success with 0`(){
        val result = validateQuantity("0", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("0")
    }
    
    @Test
    fun `0 amount and water unit FL_OZ, returns success with 0`(){
        val result = validateQuantity("0", WaterUnit.FL_OZ)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("0")
    }
    
    @Test
    fun `1 digit amount and water unit ML, returns success with same amount`(){
        val result = validateQuantity("7", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("7")
    }
    
    @Test
    fun `1 digit amount and water unit FL_OZ, returns success with same amount`(){
        val result = validateQuantity("3", WaterUnit.FL_OZ)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("3")
    }
    
    @Test
    fun `2 digit amount and water unit ML, returns success with same amount`(){
        val result = validateQuantity("74", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("74")
    }
    
    @Test
    fun `2 digit amount and water unit FL_OZ, returns success with same amount`(){
        val result = validateQuantity("58", WaterUnit.FL_OZ)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("58")
    }
    
    @Test
    fun `3 digit amount and water unit ML, returns success with same amount`(){
        val result = validateQuantity("528", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("528")
    }
    
    @Test
    fun `3 digit amount and water unit FL_OZ, returns failure`(){
        val result = validateQuantity("158", WaterUnit.FL_OZ)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo("Cannot exceed maximum limit")
    }
    
    @Test
    fun `negative 1 digit amount and water unit ML, returns success with same amount in positive`(){
        val result = validateQuantity("-1", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("1")
    }
    
    @Test
    fun `negative 1 digit amount and water unit FL_OZ, returns success with same amount in positive`(){
        val result = validateQuantity("-1", WaterUnit.FL_OZ)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("1")
    }
    
    @Test
    fun `negative 2 digit amount and water unit ML, returns success with same amount in positive`(){
        val result = validateQuantity("-13", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("13")
    }
    
    @Test
    fun `negative 2 digit amount and water unit FL_OZ, returns success with same amount in positive`(){
        val result = validateQuantity("-15", WaterUnit.FL_OZ)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("15")
    }
    
    @Test
    fun `negative 3 digit amount and water unit ML, returns success with same amount in positive`(){
        val result = validateQuantity("-123", WaterUnit.ML)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("123")
    }
    
    @Test
    fun `negative 3 digit amount and water unit FL_OZ, returns failure`(){
        val result = validateQuantity("-115", WaterUnit.FL_OZ)
        
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo("Cannot exceed maximum limit")
    }
    
}