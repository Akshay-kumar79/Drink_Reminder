package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetAllTrackableDrinksTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var getAllTrackableDrink: GetAllTrackableDrinks
    
    @BeforeEach
    fun setUp() {
        waterRepository = FakeWaterRepository()
        getAllTrackableDrink = GetAllTrackableDrinks(waterRepository)
    }
    
    @Test
    fun `returns all trackableDrinks that is available`() = runBlocking {
        val drinks = mutableListOf<TrackableDrink>()
        
        for (i in 0..4) {
            val drink = TrackableDrink(
                amount = i.toDouble(),
                unit = WaterUnit.FL_OZ
            )
            waterRepository.insertTrackableDrink(drink)
            drinks.add(drink)
        }
        
        val allTrackableDrinks = getAllTrackableDrink().first()
        
        assertThat(allTrackableDrinks.size).isEqualTo(5)
        allTrackableDrinks.forEach {
            assertThat(drinks.contains(it)).isTrue()
        }
    }
    
    @Test
    fun `returns 0 trackableDrinks if there is no drink`() = runBlocking {
        assertThat(getAllTrackableDrink().first().size).isEqualTo(0)
    }
    
}