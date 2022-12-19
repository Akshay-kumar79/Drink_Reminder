package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DeleteTrackableDrinkTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var deleteTrackableDrink: DeleteTrackableDrink
    
    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        deleteTrackableDrink = DeleteTrackableDrink(waterRepository)
    }
    
    @Test
    fun `delete trackableDrink`() = runBlocking {
        val drink = TrackableDrink(
            amount = 32.0,
            unit = WaterUnit.FL_OZ
        )
        waterRepository.insertTrackableDrink(drink)
    
        waterRepository.getAllTrackableDrinks().collectLatest {
            Truth.assertThat(it.size).isEqualTo(1)
        }
    
        deleteTrackableDrink(drink)
    
        waterRepository.getAllTrackableDrinks().collectLatest {
            Truth.assertThat(it.size).isEqualTo(0)
        }
    }
}