package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DeleteDrinkTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var deleteDrink: DeleteDrink
    
    @Before
    fun setUp() {
        waterRepository = com.akshaw.drinkreminder.waterdomain.repository.FakeWaterRepository()
        deleteDrink = DeleteDrink(waterRepository)
    }
    
    // Bass ek hi hai
    @Test
    fun `delete drink`() = runBlocking{
        val drink = Drink(
            dateTime = LocalDateTime.now(),
            waterIntake = 32.0,
            unit = WaterUnit.FL_OZ
        )
        waterRepository.insertDrink(drink)
    
        waterRepository.getAllDrink().collectLatest {
            assertThat(it.size).isEqualTo(1)
        }
        
        deleteDrink(drink)
        
        waterRepository.getAllDrink().collectLatest {
            assertThat(it.size).isEqualTo(0)
        }
    }
}