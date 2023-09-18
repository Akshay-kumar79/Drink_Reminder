package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth

class FilterAMonthDrinkTest {
    
    private lateinit var filterAMonthDrink: FilterAMonthDrink
    private lateinit var allDrinks: List<Drink>
    
    @Before
    fun setUp() {
        filterAMonthDrink = FilterAMonthDrink()
    
        val localDateTimes = mutableListOf<LocalDateTime>()
        localDateTimes.apply {
        
            add(LocalDateTime.of(LocalDate.now().year, Month.JANUARY, 4, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.JANUARY, 14, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.FEBRUARY, 16, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.FEBRUARY, 28, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.MARCH, 23, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.MARCH, 11, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.APRIL, 17, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.APRIL, 6, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.MAY, 29, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.MAY, 31, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.JUNE, 30, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.JUNE, 22, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.JULY, 12, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.JULY, 6, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.AUGUST, 3, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.AUGUST, 31, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.SEPTEMBER, 8, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.SEPTEMBER, 7, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.OCTOBER, 7, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.OCTOBER, 21, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.NOVEMBER, 1, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.NOVEMBER, 19, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.DECEMBER, 14, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year, Month.DECEMBER, 5, 2, 3))
        
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.JANUARY, 4, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.JANUARY, 14, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.FEBRUARY, 16, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.FEBRUARY, 28, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.MARCH, 23, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.MARCH, 11, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.APRIL, 17, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.APRIL, 6, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.MAY, 29, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.MAY, 31, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.JUNE, 30, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.JUNE, 22, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.JULY, 12, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.JULY, 6, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.AUGUST, 3, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.AUGUST, 31, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.SEPTEMBER, 8, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.SEPTEMBER, 7, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.OCTOBER, 7, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.OCTOBER, 21, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.NOVEMBER, 1, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.NOVEMBER, 19, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.DECEMBER, 14, 2, 3))
            add(LocalDateTime.of(LocalDate.now().year - 1, Month.DECEMBER, 5, 2, 3))
        
        }
    
        val drinksToInsert = mutableListOf<Drink>()
        localDateTimes.forEachIndexed { index, dateTime ->
            drinksToInsert.add(
                Drink(
                    id = index.toLong(),
                    dateTime = dateTime,
                    waterIntake = 1.0,
                    unit = if (index % 2 == 0) WaterUnit.ML else WaterUnit.FL_OZ
                )
            )
        }
    
        allDrinks = drinksToInsert
    }
    
    @Test
    fun `current month, return this months drinks`(){
        val filteredDrinks = filterAMonthDrink(YearMonth.now(), allDrinks)
    
        assertThat(filteredDrinks.size).isGreaterThan(0)
        filteredDrinks.forEach {
            val dateTime = it.dateTime
            assertThat(YearMonth.of(dateTime.year, dateTime.month)).isEqualTo(YearMonth.now())
        }
    }
    
    @Test
    fun `previous month, return that months drinks only`(){
        val filteredDrinks = filterAMonthDrink(YearMonth.now().minusMonths(1), allDrinks)
    
        assertThat(filteredDrinks.size).isGreaterThan(0)
        filteredDrinks.forEach {
            val dateTime = it.dateTime
            assertThat(YearMonth.of(dateTime.year, dateTime.month)).isEqualTo(YearMonth.now().minusMonths(1))
        }
    }
    
    @Test
    fun `2 month back, return that months drinks only`(){
        val filteredDrinks = filterAMonthDrink(YearMonth.now().minusMonths(2), allDrinks)
    
        assertThat(filteredDrinks.size).isGreaterThan(0)
        filteredDrinks.forEach {
            val dateTime = it.dateTime
            assertThat(YearMonth.of(dateTime.year, dateTime.month)).isEqualTo(YearMonth.now().minusMonths(2))
        }
    }
    
    @Test
    fun `previous year same month, return that months drinks only`(){
        val filteredDrinks = filterAMonthDrink(YearMonth.now().minusYears(1), allDrinks)
    
        assertThat(filteredDrinks.size).isGreaterThan(0)
        filteredDrinks.forEach {
            val dateTime = it.dateTime
            assertThat(YearMonth.of(dateTime.year, dateTime.month)).isEqualTo(YearMonth.now().minusYears(1))
        }
    }
    
    @Test
    fun `month with 0 drink, return 0 drinks`(){
        val filteredDrinks = filterAMonthDrink(YearMonth.now().plusYears(1), allDrinks)
    
        assertThat(filteredDrinks.size).isEqualTo(0)
    }
    
}