package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FilterTrackableDrinksByUnitTest {
    
    private lateinit var filterTrackableDrinksByUnit: FilterTrackableDrinksByUnit
    
    @BeforeEach
    fun setUp() {
        filterTrackableDrinksByUnit = FilterTrackableDrinksByUnit()
    }
    
    @Test
    fun `mixed trackable drinks and preference unit ML, returns ML drinks only`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 30.0, WaterUnit.ML))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 50.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 70.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 80.0, WaterUnit.ML))
            add(TrackableDrink(0, 90.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 100.0, WaterUnit.ML))
        }
        
        doTest(trackableDrinks, WaterUnit.ML, 5)
    }
    
    @Test
    fun `ML trackable drinks only and preference unit ML, returns ML drinks only`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 30.0, WaterUnit.ML))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 80.0, WaterUnit.ML))
        }
        
        doTest(trackableDrinks, WaterUnit.ML, 4)
    }
    
    @Test
    fun `FL_OZ trackable drinks only and preference unit ML, returns 0 drinks`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 50.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 70.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 90.0, WaterUnit.FL_OZ))
        }
        
        doTest(trackableDrinks, WaterUnit.ML, 0)
    }
    
    @Test
    fun `0 trackable drinks and preference unit ML, returns 0 drinks`() {
        val trackableDrinks = mutableListOf<TrackableDrink>()
        
        doTest(trackableDrinks, WaterUnit.ML, 0)
    }
    
    
    @Test
    fun `mixed trackable drinks and preference unit FL_OZ, returns FL_OZ drinks only`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 30.0, WaterUnit.ML))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 50.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 70.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 80.0, WaterUnit.ML))
            add(TrackableDrink(0, 90.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 100.0, WaterUnit.ML))
        }
        
        doTest(trackableDrinks, WaterUnit.FL_OZ, 5)
    }
    
    @Test
    fun `ML trackable drinks only and preference unit FL_OZ, returns 0 drinks`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 100.0, WaterUnit.ML))
        }
        
        doTest(trackableDrinks, WaterUnit.FL_OZ, 0)
    }
    
    @Test
    fun `FL_OZ trackable drinks only and preference unit FL_OZ, returns FL_OZ drinks only`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
        }
        
        doTest(trackableDrinks, WaterUnit.FL_OZ, 2)
    }
    
    @Test
    fun `0 trackable drinks and preference unit FL_OZ, returns 0 drinks`() {
        val trackableDrinks = mutableListOf<TrackableDrink>()
        
        doTest(trackableDrinks, WaterUnit.FL_OZ, 0)
    }
    
    
    private fun doTest(trackableDrinks: MutableList<TrackableDrink>, waterUnit: WaterUnit, expectedSize: Int) = runBlocking {
        
        val filteredTrackableDrinks = filterTrackableDrinksByUnit(waterUnit, trackableDrinks)
        assertThat(filteredTrackableDrinks.size).isEqualTo(expectedSize)
        assertThat(filteredTrackableDrinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != waterUnit } })
        
    }
    
}