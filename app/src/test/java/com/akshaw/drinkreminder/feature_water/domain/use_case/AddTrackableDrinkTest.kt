package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddTrackableDrinkTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var addTrackableDrink: AddTrackableDrink
    
    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        addTrackableDrink = AddTrackableDrink(waterRepository)
    }
    
    @Test
    fun `add drink with waterUnit ML, returns success`() {
        runBlocking {
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = 0.0,
                    unit = WaterUnit.ML
                )
            )
            assertThat(result.isSuccess).isTrue()
        }
    }
    
    @Test
    fun `add drink with waterUnit FL_OZ, returns success`() {
        runBlocking {
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = 0.0,
                    unit = WaterUnit.FL_OZ
                )
            )
            assertThat(result.isSuccess).isTrue()
        }
    }
    
    @Test
    fun `add drink with waterUnit INVALID, returns failure`() {
        runBlocking {
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = 0.0,
                    unit = WaterUnit.INVALID
                )
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Invalid water unit")
        }
    }
}