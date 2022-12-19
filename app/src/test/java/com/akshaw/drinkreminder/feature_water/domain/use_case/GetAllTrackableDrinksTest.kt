package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllTrackableDrinksTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var getAllTrackableDrink: GetAllTrackableDrinks
    
    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        getAllTrackableDrink = GetAllTrackableDrinks(waterRepository)
    }
    
    @Test
    fun `returns all trackableDrinks that is available`() = runBlocking {
        for (i in 0..4) {
            waterRepository.insertTrackableDrink(
                TrackableDrink(
                    amount = i.toDouble(),
                    unit = WaterUnit.FL_OZ
                )
            )
        }
        
        getAllTrackableDrink().collectLatest {
            assertThat(it.size).isEqualTo(5)
        }
    }
    
    @Test
    fun `returns 0 trackableDrinks if there is no drink`() = runBlocking {
        getAllTrackableDrink().collectLatest {
            assertThat(it.size).isEqualTo(0)
        }
    }
    
}