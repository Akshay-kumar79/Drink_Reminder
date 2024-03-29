package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetWeeklyAverageCompletionTest {
    
    private lateinit var filterADayDrinks: FilterADayDrinks
    private lateinit var getDrinkProgress: GetDrinkProgress
    private lateinit var getWeeklyAverageCompletion: GetWeeklyAverageCompletion
    
    @BeforeEach
    fun setUp() {
        filterADayDrinks = FilterADayDrinks()
        getDrinkProgress = GetDrinkProgress()
        getWeeklyAverageCompletion = GetWeeklyAverageCompletion(
            filterADayDrinks,
            getDrinkProgress
        )
    }
    
    @Test
    fun `first day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 2400.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `second day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `third day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `fourth day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `fifth day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `sixth day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `seventh day completed will return 1 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `two day in the week completed will return 2 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0 * 2.0 /7.0)
    }
    
    @Test
    fun `two day in the week completed will return 3 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0 * 3.0 /7.0)
    }
    
    @Test
    fun `two day in the week completed will return 4 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0 * 4.0 /7.0)
    }
    
    @Test
    fun `two day in the week completed will return 5 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0 * 5.0 /7.0)
    }
    
    @Test
    fun `two day in the week completed will return 6 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0 * 6.0 /7.0)
    }
    
    @Test
    fun `two day in the week completed will return 7 by 7 of 100`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 2400.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 2400.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(100.0 * 7.0 /7.0)
    }
    
    @Test
    fun `zero day in the week completed will return 0`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val averageCompletion = getWeeklyAverageCompletion(allDrinks, 2350.0, WaterUnit.ML)
        
        assertThat(averageCompletion).isEqualTo(0.0)
    }
    
}