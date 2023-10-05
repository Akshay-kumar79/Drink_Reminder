package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteTrackableDrinkTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var deleteTrackableDrink: DeleteTrackableDrink
    
    @BeforeEach
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
        assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
    
        deleteTrackableDrink(drink)
        assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(0)
    }
}