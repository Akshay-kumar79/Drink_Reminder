package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.util.ChartType
import java.time.*
import javax.inject.Inject
import kotlin.math.ceil

/**
 *  returns
 *  -> list of size 7 with progress of the week in percentage (0..100), if selectedChart is of type WEEK
 *  -> list of size 12 with progress of the year in percentage (0..100), if selectedChart is of type YEAR
 */
class GetReportChartData @Inject constructor(
    private val getDrinkProgress: GetDrinkProgress,
    private val filterADayDrinks: FilterADayDrinks,
    private val filterAMonthDrink: FilterAMonthDrink
) {
    
    operator fun invoke(
        allDrinks: List<com.akshaw.drinkreminder.waterdomain.model.Drink>,
        selectedChart: ChartType,
        chartSelectedWeeksFirstDay: LocalDate,
        chartSelectedYear: Year,
        goal: Double
    ): List<Int> {
        val data = mutableListOf<Int>()
        when (selectedChart) {
            ChartType.WEEK -> {
                DayOfWeek.values().forEach {
                    val day = chartSelectedWeeksFirstDay.plusDays(it.value - 1L)
                    val progress = getDrinkProgress(filterADayDrinks(
                        day,
                        allDrinks
                    ))
                    val percent = progress * 100 / goal
                    data.add(
                        ceil(percent).toInt().coerceIn(0, 100)
                    )
                }
            }
            ChartType.YEAR -> {
                Month.values().forEach {
                    val month = YearMonth.of(chartSelectedYear.value, it)
                    val totalProgress = getDrinkProgress(filterAMonthDrink(
                        month,
                        allDrinks
                    ))
                    val numberOfDayInMonth = month.lengthOfMonth()
                    val percent = (totalProgress * 100)/(goal * numberOfDayInMonth)
                    data.add(
                        ceil(percent).toInt().coerceIn(0, 100)
                    )
                }
            }
        }
        return data
    }
    
}