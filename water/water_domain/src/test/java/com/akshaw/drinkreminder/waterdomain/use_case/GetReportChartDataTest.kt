package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.model.Drink
import com.akshaw.drinkreminder.core.util.ChartType
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import kotlin.math.ceil

class GetReportChartDataTest {
    
    private lateinit var getDrinkProgress: GetDrinkProgress
    private lateinit var filterADayDrinks: FilterADayDrinks
    private lateinit var filterAMonthDrink: FilterAMonthDrink
    private lateinit var getReportChartData: GetReportChartData
    
    @Before
    fun setUp() {
        getDrinkProgress = GetDrinkProgress()
        filterADayDrinks = FilterADayDrinks()
        filterAMonthDrink = FilterAMonthDrink()
        getReportChartData = GetReportChartData(
            getDrinkProgress,
            filterADayDrinks,
            filterAMonthDrink
        )
    }
    
    @Test
    fun `selected chart type WEEK, starting week day today, 0 drinks, return list of size 7 with all 0`() {
        val allDrinks = emptyList<Drink>()
        val data = getReportChartData(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(7)
        
        data.forEach {
            assertThat(it).isEqualTo(0)
        }
    }
    
    @Test
    fun `selected chart type YEAR, current year this year, 0 drinks, return list of size 12 with all 0`() {
        val allDrinks = emptyList<Drink>()
        val data = getReportChartData(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(12)
        
        data.forEach {
            assertThat(it).isEqualTo(0)
        }
    }
    
    @Test
    fun `selected chart type WEEK, starting week day today, all drink out of current week, return list of size 7 with all 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusDays(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(7)
        
        data.forEach {
            assertThat(it).isEqualTo(0)
        }
    }
    
    @Test
    fun `selected chart type WEEK, starting week day today, only one drink on first day, return list of size 7 with first one calculated progress and all other 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(7)
        
        data.forEachIndexed { index, progress ->
            if (index == 0)
                assertThat(progress).isEqualTo(ceil(20 * 100 / 2350.0).toInt().coerceIn(0, 100))
            else
                assertThat(progress).isEqualTo(0)
        }
    }

    @Test
    fun `selected chart type WEEK, starting week day 3 days back, only one drink on first day, return list of size 7 with first one calculated progress and all other 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().plusDays(8), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(7), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now().minusDays(3),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(7)
        
        data.forEachIndexed { index, progress ->
            if (index == 0)
                assertThat(progress).isEqualTo(ceil(20 * 100 / 2350.0).toInt().coerceIn(0, 100))
            else
                assertThat(progress).isEqualTo(0)
        }
    }

    @Test
    fun `selected chart type WEEK, starting week day today, one drinks on first and last day, return list of size 7 with first and last one calculated progress and all other 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now(), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(6), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.WEEK,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(7)
        
        data.forEachIndexed { index, progress ->
            if (index == 0 || index == data.lastIndex)
                assertThat(progress).isEqualTo(ceil(20 * 100 / 2350.0).toInt().coerceIn(0, 100))
            else
                assertThat(progress).isEqualTo(0)
        }
    }

    @Test
    fun `selected chart type YEAR, current year is selected, all drink out of current year, return list of size 12 with all 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.now().minusYears(1), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(12)
        
        data.forEach {
            assertThat(it).isEqualTo(0)
        }
    }
    
    @Test
    fun `selected chart type YEAR, current year is selected, only one drink on first month, return list of size 12 with first one calculated progress and all other 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 3, 3, 3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(2), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusYears(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(12)
        
        data.forEachIndexed { index, progress ->
            if (index == 0)
                assertThat(progress).isEqualTo(ceil(20 * 100 / 2350.0).toInt().coerceIn(0, 100))
            else
                assertThat(progress).isEqualTo(0)
        }
    }

    @Test
    fun `selected chart type YEAR, 3 year back is selected, only one drink on first month, return list of size 12 with first one calculated progress and all other 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.of(Year.now().minusYears(3).value, Month.JANUARY, 4, 4, 4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusDays(7), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().minusDays(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now().minusYears(3),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(12)
        
        data.forEachIndexed { index, progress ->
            if (index == 0)
                assertThat(progress).isEqualTo(ceil(20 * 100 / 2350.0).toInt().coerceIn(0, 100))
            else
                assertThat(progress).isEqualTo(0)
        }
    }

    @Test
    fun `selected chart type YEAR, current year is selected, one drinks on first and last month, return list of size 12 with first and last one calculated progress and all other 0`() {
        val allDrinks = mutableListOf<Drink>().apply {
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.JANUARY, 3, 3, 3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.of(Year.now().value, Month.DECEMBER, 3, 3, 3), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusYears(6), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusYears(4), 20.0, WaterUnit.ML))
            add(Drink(0, LocalDateTime.now().plusYears(4), 20.0, WaterUnit.ML))
        }
        val data = getReportChartData(
            allDrinks,
            ChartType.YEAR,
            LocalDate.now(),
            Year.now(),
            2350.0,
            WaterUnit.ML
        )
        
        assertThat(data.size).isEqualTo(12)
        
        data.forEachIndexed { index, progress ->
            if (index == 0 || index == data.lastIndex)
                assertThat(progress).isEqualTo(ceil(20 * 100 / 2350.0).toInt().coerceIn(0, 100))
            else
                assertThat(progress).isEqualTo(0)
        }
    }

}