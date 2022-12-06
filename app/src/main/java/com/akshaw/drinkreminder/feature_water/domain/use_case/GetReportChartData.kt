package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import java.time.*
import javax.inject.Inject

class GetReportChartData @Inject constructor(
    private val getDrinkProgress: GetDrinkProgress,
    private val filterADayDrinks: FilterADayDrinks,
    private val filterAMonthDrink: FilterAMonthDrink
) {
    
    operator fun invoke(
        allDrinks: List<Drink>,
        selectedChart: ChartType,
        chartSelectedWeeksFirstDay: LocalDate,
        chartSelectedYear: Year
    ): List<Double> {
        val data = mutableListOf<Double>()
        when (selectedChart) {
            ChartType.WEEK -> {
                DayOfWeek.values().forEach {
                    data.add(
                        getDrinkProgress(filterADayDrinks(
                            chartSelectedWeeksFirstDay.plusDays(it.value - 1L),
                            allDrinks
                        ))
                    )
                }
            }
            ChartType.YEAR -> {
                Month.values().forEach {
                    data.add(
                        getDrinkProgress(filterAMonthDrink(
                            YearMonth.of(chartSelectedYear.value, it),
                            allDrinks
                        ))
                    )
                }
            }
        }
        return data
    }
    
}