package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetAllDrinksTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var getAllDrinks: GetAllDrinks
    
    @BeforeEach
    fun setUp() {
        waterRepository = FakeWaterRepository()
        getAllDrinks = GetAllDrinks(waterRepository)
    }
    
    @Test
    fun `returns all drinks that is available`() = runBlocking {
        
        val drinks = mutableListOf<Drink>()
        
        for (i in 0..4) {
            val drink = Drink(
                dateTime = LocalDateTime.now(),
                waterIntake = i.toDouble(),
                unit = WaterUnit.FL_OZ
            )
            drinks.add(drink)
            waterRepository.insertDrink(drink)
        }
        
        val allDrinks = getAllDrinks()
        assertThat(allDrinks.first().size).isEqualTo(5)
        drinks.forEach {
            assertThat(allDrinks.first().contains(it)).isTrue()
        }
    }
    
    @Test
    fun `returns 0 drinks if there is no drink`() = runBlocking {
        assertThat(getAllDrinks().first().size).isEqualTo(0)
    }
}