package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class GetWeeklyAverageFrequencyTest {
    
    private lateinit var filterADayDrinks: FilterADayDrinks
    private lateinit var getWeeklyAverageFrequency: GetWeeklyAverageFrequency
    
    @Before
    fun setUp() {
        filterADayDrinks = FilterADayDrinks()
        getWeeklyAverageFrequency = GetWeeklyAverageFrequency(filterADayDrinks)
    }
    
    @Test
    fun `0 drinks will return 0`(){
        val allDrinks = emptyList<Drink>()
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(0)
    }
    
    @Test
    fun `1 drink every day will return 1`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(1)
    }
    
    @Test
    fun `2 drink every day will return 2`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(2)
    }
    
    @Test
    fun `18 drinks same day will return 3`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(3)
    }
    
    @Test
    fun `only one drink will return 1`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(1)
    }
    
    @Test
    fun `8 drink will return 2`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(2)
    }
    
    @Test
    fun `all drink in some other week will return 0`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().plusWeeks(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(6), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(0)
    }
    
    @Test
    fun `3 drink in week and other in some other week will return 1`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusWeeks(6), 20.0, WaterUnit.ML))
        }
        
        val averageFrequency = getWeeklyAverageFrequency(allDrinks)
        
        assertThat(averageFrequency).isEqualTo(1)
    }
    
    
}