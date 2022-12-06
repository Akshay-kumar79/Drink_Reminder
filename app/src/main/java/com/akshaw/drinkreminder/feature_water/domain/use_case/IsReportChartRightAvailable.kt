package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import java.time.LocalDate
import java.time.Year

class IsReportChartRightAvailable {
    
    operator fun invoke(
        allDrinks: List<Drink>,
        selectedChart: ChartType,
        chartSelectedWeeksFirstDay: LocalDate,
        chartSelectedYear: Year
    ): Boolean {
        
        return when (selectedChart) {
            ChartType.WEEK -> {
                var isAvailable = false
                val lastDayOfWeek = chartSelectedWeeksFirstDay
                    .plusWeeks(1)
                    .minusDays(1)
                allDrinks.forEach {
                    isAvailable = lastDayOfWeek.isBefore(it.dateTime.toLocalDate()) &&
                            LocalDate.now().isAfter(lastDayOfWeek)
                    if (isAvailable)
                        return@forEach
                }
                isAvailable
            }
            ChartType.YEAR -> {
                var isAvailable = false
                allDrinks.forEach {
                    isAvailable = Year.of(it.dateTime.year).isAfter(chartSelectedYear)
                            && Year.of(LocalDate.now().year).isBefore(chartSelectedYear)
                    if (isAvailable)
                        return@forEach
                }
                isAvailable
            }
        }
        
    }
}