package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

class GetADayDrinksTest {

    private lateinit var getADayDrinks: GetADayDrinks
    private lateinit var waterRepository: WaterRepository

    @Before
    fun setUp() {
        waterRepository = FakeWaterRepository()
        getADayDrinks = GetADayDrinks(waterRepository)

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


            removeIf { it.year == LocalDate.now().year && it.month == LocalDate.now().minusMonths(1).month }
            removeIf { it.year == LocalDate.now().year && it.month == LocalDate.now().month }

            add(LocalDateTime.now())
            add(LocalDateTime.now().minusDays(1))
            add(LocalDateTime.now().minusDays(2))
            add(LocalDateTime.now().minusDays(3))

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

        runBlocking {
            drinksToInsert.forEach {
                waterRepository.insertDrink(it)
            }
        }
    }

    @Test
    fun `today date, return today's drinks only`() {
        runBlocking {
            getADayDrinks(LocalDate.now()).collectLatest {
                assertThat(it.size).isGreaterThan(0)
                it.forEach {
                    assertThat(it.dateTime.toLocalDate()).isEqualTo(LocalDate.now())
                }
            }
        }
    }

    @Test
    fun `yesterday date, return yesterday's drinks only`() {
        runBlocking {
            getADayDrinks(LocalDate.now().minusDays(1)).collectLatest {
                assertThat(it.size).isGreaterThan(0)
                it.forEach {
                    assertThat(it.dateTime.toLocalDate()).isEqualTo(LocalDate.now().minusDays(1))
                }
            }
        }
    }

    @Test
    fun `day before yesterday date, return day before yesterday's drink only`() {
        runBlocking {
            getADayDrinks(LocalDate.now().minusDays(2)).collectLatest {
                assertThat(it.size).isGreaterThan(0)
                it.forEach {
                    assertThat(it.dateTime.toLocalDate()).isEqualTo(LocalDate.now().minusDays(2))
                }
            }
        }
    }

    @Test
    fun `previous year date, return that's days drink only`() {
        runBlocking {
            getADayDrinks(LocalDate.of(LocalDate.now().year - 1, Month.AUGUST, 31)).collectLatest {
                assertThat(it.size).isGreaterThan(0)
                it.forEach {
                    assertThat(it.dateTime.toLocalDate()).isEqualTo(LocalDate.of(LocalDate.now().year - 1, Month.AUGUST, 31))
                }
            }
        }
    }

    @Test
    fun `date have 0 drink, returns 0 drink`() {
        runBlocking {
            getADayDrinks(LocalDate.now().minusDays(5)).collectLatest {
                assertThat(it.size).isEqualTo(0)
            }
        }
    }
}