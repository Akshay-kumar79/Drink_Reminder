package com.akshaw.drinkreminder.onboarding_domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.use_case.AddTrackableDrink
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWaterQuantityByUnit
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.floor

class SaveInitialTrackableDrinksTest {
    
    private lateinit var preference: FakePreference
    private lateinit var waterRepository: FakeWaterRepository
    private lateinit var addTrackableDrink: AddTrackableDrink
    private lateinit var changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
    private lateinit var saveInitialTrackableDrinks: SaveInitialTrackableDrinks
    
    @BeforeEach
    fun setUp() {
        preference = FakePreference()
        waterRepository = FakeWaterRepository()
        addTrackableDrink = AddTrackableDrink(waterRepository)
        changeWaterQuantityByUnit = ChangeWaterQuantityByUnit()
        saveInitialTrackableDrinks = SaveInitialTrackableDrinks(preference, addTrackableDrink, changeWaterQuantityByUnit)
    }
    
    @Test
    fun `saveInitialTrackableDrinks when onboarding is not completed test`() = runBlocking {
        preference.saveIsOnboardingCompleted(false)
        waterRepository.trackableDrinks.clear()
        
        assertThat(waterRepository.trackableDrinks.size).isEqualTo(0)
        saveInitialTrackableDrinks()
        
        // size is multiplied by 2 because there are 2 water units available
        assertThat(waterRepository.trackableDrinks.size).isEqualTo(Constants.DEFAULT_TRACKABLE_DRINKS.size * 2)
        
        Constants.DEFAULT_TRACKABLE_DRINKS.forEach { amountInMl ->
            val hasMlTrackableDrinks = waterRepository.trackableDrinks.any { it.unit == WaterUnit.ML && it.amount == amountInMl }
            val hasFLOZTrackableDrink = waterRepository.trackableDrinks.any {
                it.unit == WaterUnit.FL_OZ && it.amount == floor(changeWaterQuantityByUnit(amountInMl, WaterUnit.ML, WaterUnit.FL_OZ).getOrThrow())
            }
            
            assertThat(hasMlTrackableDrinks).isTrue()
            assertThat(hasFLOZTrackableDrink).isTrue()
        }
    }
    
    @Test
    fun `saveInitialTrackableDrinks when onboarding is completed test`() = runBlocking {
        preference.saveIsOnboardingCompleted(true)
        waterRepository.trackableDrinks.clear()
        
        assertThat(waterRepository.trackableDrinks.size).isEqualTo(0)
        saveInitialTrackableDrinks()
        
        assertThat(waterRepository.trackableDrinks.size).isEqualTo(0)
    }
}