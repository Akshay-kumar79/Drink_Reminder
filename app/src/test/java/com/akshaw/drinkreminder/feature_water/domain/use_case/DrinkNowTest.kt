package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DrinkNowTest {

    private lateinit var waterRepository: WaterRepository
    private lateinit var preferences: Preferences
    private lateinit var addDrink: AddDrink
    private lateinit var drinkNow: DrinkNow

    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        preferences = com.akshaw.drinkreminder.core.FakePreference()
        addDrink = AddDrink(waterRepository)
        drinkNow = DrinkNow(preferences, addDrink)
    }

    @Test
    fun `trackableDrink with id -1, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(-1L, 0.0, WaterUnit.ML)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Add some drinks")
        }
    }
    
    @Test
    fun `trackableDrink with amount 0, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, 0.0, WaterUnit.ML)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Drink valid quantity")
        }
    }
    
    @Test
    fun `trackableDrink with negative amount, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, -24.0, WaterUnit.ML)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Drink valid quantity")
        }
    }
    
    @Test
    fun `trackableDrink with id -1 and water unit ML, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(-1L, 0.0, WaterUnit.ML)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Add some drinks")
        }
    }
    
    @Test
    fun `trackableDrink with amount 0 and water unit ML, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, 0.0, WaterUnit.ML)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Drink valid quantity")
        }
    }
    
    @Test
    fun `trackableDrink with negative amount and water unit ML, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, -24.0, WaterUnit.ML)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Drink valid quantity")
        }
    }
    
    @Test
    fun `trackableDrink with id -1 and water unit FL_OZ, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(-1L, 0.0, WaterUnit.FL_OZ)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Add some drinks")
        }
    }
    
    @Test
    fun `trackableDrink with amount 0 and water unit FL_OZ, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, 0.0, WaterUnit.FL_OZ)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Drink valid quantity")
        }
    }
    
    @Test
    fun `trackableDrink with negative amount and water unit FL_OZ, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, -24.0, WaterUnit.FL_OZ)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Drink valid quantity")
        }
    }
    
    @Test
    fun `trackableDrink with water type INVALID, returns failure`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(1L, 24.0, WaterUnit.INVALID)
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo("Invalid water unit")
        }
    }
    
    @Test
    fun `genuine trackableDrink, returns success with id`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(0L, 2.0, WaterUnit.ML)
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()).isEqualTo(-1)
        }
    }
}