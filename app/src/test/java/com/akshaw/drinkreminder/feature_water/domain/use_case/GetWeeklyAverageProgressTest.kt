package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.data.preferences.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class GetWeeklyAverageProgressTest {
    
    private lateinit var preference: Preferences
    private lateinit var getDrinkProgress: GetDrinkProgress
    private lateinit var filterADayDrinks: FilterADayDrinks
    private lateinit var getWeeklyAverageProgress: GetWeeklyAverageProgress
    
    @Before
    fun setUp() {
        preference = FakePreference()
        getDrinkProgress = GetDrinkProgress(preference)
        filterADayDrinks = FilterADayDrinks()
        getWeeklyAverageProgress = GetWeeklyAverageProgress(
            getDrinkProgress,
            filterADayDrinks
        )
    }
    
    @Test
    fun `0 drinks will return 0 average progress`(){
        val allDrinks = emptyList<Drink>()
        val progress = getWeeklyAverageProgress(allDrinks)
        
        assertThat(progress).isEqualTo(0.0)
    }
    
    @Test
    fun `1 drink every day with intake 50 will return 50 average progress`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 50.0, WaterUnit.ML))
        }
        val progress = getWeeklyAverageProgress(allDrinks)
        
        assertThat(progress).isEqualTo(50.0)
    }
    
    @Test
    fun `2 drink every day with intake 50 will return 100 average progress`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now(), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 50.0, WaterUnit.ML))
        }
        val progress = getWeeklyAverageProgress(allDrinks)
        
        assertThat(progress).isEqualTo(100.0)
    }
    
    @Test
    fun `1 drink on first and last day with intake 50 will return 100 by 7 average progress`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 50.0, WaterUnit.ML))
        }
        val progress = getWeeklyAverageProgress(allDrinks)
        
        assertThat(progress).isEqualTo(100.0/7.0)
    }
    
    @Test
    fun `1 drink on first and other out of week with intake 50 will return 50 by 7 average progress`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 50.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(24), 50.0, WaterUnit.ML))
        }
        val progress = getWeeklyAverageProgress(allDrinks)
        
        assertThat(progress).isEqualTo(50.0/7.0)
    }
    
    
    
}