package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

class IsReportChartLeftAvailableTest {
    
    private lateinit var isReportChartLeftAvailable: IsReportChartLeftAvailable
    
    @Before
    fun setUp() {
        isReportChartLeftAvailable = IsReportChartLeftAvailable()
    }
    
    @Test
    fun `0 drinks, chart type WEEK, week first day today, current year, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `0 drinks, chart type WEEK, week first day 6 day back, current year, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks in same week, chart type WEEK, week first day 6 day back, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks after starting week day, chart type WEEK, week first day 6 day back, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(60), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks after starting week day except one, chart type WEEK, week first day 6 day back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusDays(7), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(60), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks before starting week day except one, chart type WEEK, week first day 6 day back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusDays(7), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(60), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks before starting week day, chart type WEEK, week first day 6 day back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusDays(7), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(60), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `0 drinks, chart type YEAR, week first day today, current year, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `0 drinks, chart type YEAR, week first day today, 5 years back, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now().minusYears(5)
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks are in same year, chart type YEAR, week first day today, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.FEBRUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MARCH, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.APRIL, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MAY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JUNE, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JULY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.AUGUST, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.SEPTEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.OCTOBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.NOVEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.DECEMBER, 23, 23,23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks are in same year and after this year, chart type YEAR, week first day today, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.FEBRUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MARCH, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.APRIL, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.MAY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(2).value, Month.JUNE, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(3).value, Month.JULY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(4).value, Month.AUGUST, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(5).value, Month.SEPTEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(6).value, Month.OCTOBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(7).value, Month.NOVEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(8).value, Month.DECEMBER, 23, 23,23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks are in same year and after this year except one, chart type YEAR, week first day today, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.JANUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.FEBRUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MARCH, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.APRIL, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.MAY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(2).value, Month.JUNE, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(3).value, Month.JULY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(4).value, Month.AUGUST, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(5).value, Month.SEPTEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(6).value, Month.OCTOBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(7).value, Month.NOVEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(8).value, Month.DECEMBER, 23, 23,23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are before this year except one, chart type YEAR, week first day today, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.JANUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.FEBRUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.MARCH, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.APRIL, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.MAY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(2).value, Month.JUNE, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(3).value, Month.JULY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(4).value, Month.AUGUST, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(5).value, Month.SEPTEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.OCTOBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(7).value, Month.NOVEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(8).value, Month.DECEMBER, 23, 23,23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are before this year, chart type YEAR, week first day today, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusYears(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.JANUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.FEBRUARY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.MARCH, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.APRIL, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.MAY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(2).value, Month.JUNE, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(3).value, Month.JULY, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(4).value, Month.AUGUST, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(5).value, Month.SEPTEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.OCTOBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(7).value, Month.NOVEMBER, 23, 23,23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(8).value, Month.DECEMBER, 23, 23,23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartLeftAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    
}