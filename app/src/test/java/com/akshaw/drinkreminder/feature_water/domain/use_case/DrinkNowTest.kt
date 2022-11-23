package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.data.preferences.FakePreference
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
    private lateinit var drinkNow: DrinkNow

    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        preferences = FakePreference()
        drinkNow = DrinkNow(waterRepository, preferences)
    }

    @Test
    fun `trackableDrink with id -1, returns failure`(){
        runBlocking {
            assertThat(
                drinkNow(
                    TrackableDrink(-1L, 0.0, WaterUnit.ML)
                ).isFailure
            ).isTrue()
        }
    }

    @Test
    fun `genuine trackableDrink, returns success with id`(){
        runBlocking {
            val result = drinkNow(
                TrackableDrink(0L, 0.0, WaterUnit.ML)
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()).isEqualTo(-1)
        }
    }
}