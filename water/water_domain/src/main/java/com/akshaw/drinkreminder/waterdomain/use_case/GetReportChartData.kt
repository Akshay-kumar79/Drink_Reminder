package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.waterdomain.utils.ChartType
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import java.time.*
import javax.inject.Inject
import kotlin.math.ceil

private const val MIN_CHART_VALUE = 0
private const val MAX_CHART_VALUE = 100

// Resolution of detekt warning "MagicNumber"
private const val PERCENTAGE_MAX = 100

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
        allDrinks: List<Drink>,
        selectedChart: ChartType,
        chartSelectedWeeksFirstDay: LocalDate,
        chartSelectedYear: Year,
        goal: Double,
        currentWaterUnit: WaterUnit
    ): List<Int> {
        val data = mutableListOf<Int>()
        when (selectedChart) {
            ChartType.WEEK -> {
                DayOfWeek.values().forEach {
                    val day = chartSelectedWeeksFirstDay.plusDays(it.value - 1L)
                    val progress = getDrinkProgress(filterADayDrinks(
                        day,
                        allDrinks
                    ), currentWaterUnit)
                    val percent = progress * PERCENTAGE_MAX / goal
                    data.add(
                        ceil(percent).toInt().coerceIn(MIN_CHART_VALUE, MAX_CHART_VALUE)
                    )
                }
            }
            
            ChartType.YEAR -> {
                Month.values().forEach {
                    val month = YearMonth.of(chartSelectedYear.value, it)
                    val totalProgress = getDrinkProgress(filterAMonthDrink(
                        month,
                        allDrinks
                    ), currentWaterUnit)
                    val numberOfDayInMonth = month.lengthOfMonth()
                    val percent = (totalProgress * PERCENTAGE_MAX) / (goal * numberOfDayInMonth)
                    data.add(
                        ceil(percent).toInt().coerceIn(MIN_CHART_VALUE, MAX_CHART_VALUE)
                    )
                }
            }
        }
        return data
    }
    
}