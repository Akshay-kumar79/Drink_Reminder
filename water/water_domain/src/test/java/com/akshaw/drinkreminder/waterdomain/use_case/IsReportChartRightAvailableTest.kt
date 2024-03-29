package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.waterdomain.utils.ChartType
import com.akshaw.drinkreminder.core.domain.model.Drink
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

class IsReportChartRightAvailableTest {
    
    private lateinit var isReportChartRightAvailable: IsReportChartRightAvailable
    
    @BeforeEach
    fun setUp() {
        isReportChartRightAvailable = IsReportChartRightAvailable()
    }
    
    @Test
    fun `0 drinks, chart type WEEK, week first day today, current year, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `0 drinks, chart type WEEK, week first day 6 days back, current year, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks are in same week, chart type WEEK, week first day 6 days back, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(5), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(6), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks are before current weeks last day, chart type WEEK, week first day 6 days back, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(60), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks are before current weeks last day except one is after, chart type WEEK, week first day 6 days back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(1), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are after current weeks last day except one before, chart type WEEK, week first day 6 days back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(1), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are after current weeks last day, chart type WEEK, week first day 6 days back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().plusDays(45), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(1), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(6),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are after before weeks last day, chart type WEEK, week first day 12 days back, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusDays(12), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(10), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(20), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(30), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(40), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(50), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(20), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(12),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `0 drinks, chart type YEAR, week first day today, current year, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `0 drinks, chart type YEAR, week first day today, 5 year ahead, return false`(){
        val allDrinks = emptyList<Drink>()
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now().plusYears(5)
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks in same year, chart type YEAR, week first day today, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.FEBRUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MARCH, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.APRIL, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MAY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JUNE, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JULY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.AUGUST, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.SEPTEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.OCTOBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.NOVEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.DECEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks in same year or before current year, chart type YEAR, week first day today, current year, return false`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.FEBRUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MARCH, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.APRIL, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MAY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.JUNE, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(2).value, Month.JULY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(3).value, Month.AUGUST, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(4).value, Month.SEPTEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(5).value, Month.OCTOBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.NOVEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(7).value, Month.DECEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isFalse()
    }
    
    @Test
    fun `all drinks in same year or before current year except one, chart type YEAR, week first day today, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().plusYears(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.FEBRUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MARCH, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.APRIL, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.MAY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(1).value, Month.JUNE, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(2).value, Month.JULY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(3).value, Month.AUGUST, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(4).value, Month.SEPTEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(5).value, Month.OCTOBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.NOVEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(7).value, Month.DECEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are after current year except one, chart type YEAR, week first day today, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.JANUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.FEBRUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.MARCH, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.APRIL, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.MAY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.JUNE, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(2).value, Month.JULY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(3).value, Month.AUGUST, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(4).value, Month.SEPTEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(5).value, Month.OCTOBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(6).value, Month.NOVEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(7).value, Month.DECEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are after current year, chart type YEAR, week first day today, current year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().plusYears(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.JANUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.FEBRUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.MARCH, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.APRIL, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.MAY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(1).value, Month.JUNE, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(2).value, Month.JULY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(3).value, Month.AUGUST, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(4).value, Month.SEPTEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(5).value, Month.OCTOBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(6).value, Month.NOVEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().plusYears(7).value, Month.DECEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now()
        )
        
        assertThat(isAvailable).isTrue()
    }
    
    @Test
    fun `all drinks are before current year, chart type YEAR, week first day today, 5 year back year, return true`(){
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusYears(6), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(5).value, Month.JANUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.FEBRUARY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.MARCH, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.APRIL, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.MAY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.JUNE, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.JULY, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.AUGUST, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.SEPTEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.OCTOBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(6).value, Month.NOVEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(7).value, Month.DECEMBER, 23, 23, 23), 20.0, WaterUnit.ML))
        }
        
        val isAvailable = isReportChartRightAvailable(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now().minusYears(5)
        )
        
        assertThat(isAvailable).isTrue()
    }
    
}