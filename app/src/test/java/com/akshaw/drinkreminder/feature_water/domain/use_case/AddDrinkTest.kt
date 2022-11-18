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

class AddDrinkTest {

    private lateinit var waterRepository: WaterRepository
    private lateinit var preferences: Preferences
    private lateinit var addDrink: AddDrink

    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        preferences = FakePreference()
        addDrink = AddDrink(waterRepository, preferences)
    }

    @Test
    fun `trackableDrink with id -1, returns failure`(){
        runBlocking {
            assertThat(
                addDrink(
                    TrackableDrink(-1L, 0.0, WaterUnit.ML)
                ).isFailure
            ).isTrue()
        }
    }

    @Test
    fun `genuine trackableDrink, returns success with id`(){
        runBlocking {
            val result = addDrink(
                TrackableDrink(0L, 0.0, WaterUnit.ML)
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()).isEqualTo(-1)
        }
    }
}