package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime


class AddDrinkTest {
    
    private lateinit var addDrink: AddDrink
    private lateinit var waterRepository: WaterRepository
    
    @Before
    fun setUp() {
        waterRepository = com.akshaw.drinkreminder.waterdomain.repository.FakeWaterRepository()
        addDrink = AddDrink(waterRepository)
    }
    
    @Test
    fun `add drink with waterUnit ML, returns success`() {
        runBlocking {
            val result = addDrink(
                com.akshaw.drinkreminder.waterdomain.model.Drink(
                    dateTime = LocalDateTime.now(),
                    waterIntake = 0.0,
                    unit = WaterUnit.ML
                )
            )
            assertThat(result.isSuccess).isTrue()
        }
    }
    
    @Test
    fun `add drink with waterUnit FL_OZ, returns success`() {
        runBlocking {
            val result = addDrink(
                com.akshaw.drinkreminder.waterdomain.model.Drink(
                    dateTime = LocalDateTime.now(),
                    waterIntake = 0.0,
                    unit = WaterUnit.FL_OZ
                )
            )
            assertThat(result.isSuccess).isTrue()
        }
    }
    
    @Test
    fun `add drink with waterUnit INVALID, returns failure`() {
        runBlocking {
            val result = addDrink(
                com.akshaw.drinkreminder.waterdomain.model.Drink(
                    dateTime = LocalDateTime.now(),
                    waterIntake = 0.0,
                    unit = WaterUnit.INVALID
                )
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Invalid water unit")
        }
    }
    
}