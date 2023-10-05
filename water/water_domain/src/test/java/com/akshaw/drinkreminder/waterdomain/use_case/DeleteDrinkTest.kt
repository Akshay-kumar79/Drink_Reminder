package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DeleteDrinkTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var deleteDrink: DeleteDrink
    
    @BeforeEach
    fun setUp() {
        waterRepository = FakeWaterRepository()
        deleteDrink = DeleteDrink(waterRepository)
    }
    
    @Test
    fun `delete drink`() = runBlocking {
        val drink = Drink(
            dateTime = LocalDateTime.now(),
            waterIntake = 32.0,
            unit = WaterUnit.FL_OZ
        )
        
        waterRepository.insertDrink(drink)
        assertThat(waterRepository.getAllDrink().first().size).isEqualTo(1)
        
        deleteDrink(drink)
        assertThat(waterRepository.getAllDrink().first().size).isEqualTo(0)
    }
}