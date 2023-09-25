package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class GetAllDrinksTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var getAllDrinks: GetAllDrinks
    
    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        getAllDrinks = GetAllDrinks(waterRepository)
    }
    
    @Test
    fun `returns all drinks that is available`() = runBlocking{
        for (i in 0..4){
            waterRepository.insertDrink(
                Drink(
                    dateTime = LocalDateTime.now(),
                    waterIntake = i.toDouble(),
                    unit = WaterUnit.FL_OZ
                )
            )
        }
        
        getAllDrinks().collectLatest {
            assertThat(it.size).isEqualTo(5)
        }
    }
    
    @Test
    fun `returns 0 drinks if there is no drink, obviously`() = runBlocking{
        getAllDrinks().collectLatest {
            assertThat(it.size).isEqualTo(0)
        }
    }
}