package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import java.time.LocalDate
import java.time.Year

class IsReportChartLeftAvailable {
    
    operator fun invoke(
        allDrinks: List<Drink>,
        selectedChart: ChartType,
        chartSelectedWeeksFirstDay: LocalDate,
        chartSelectedYear: Year
    ): Boolean {
        return when (selectedChart) {
            ChartType.WEEK -> {
                var isAvailable = false
                allDrinks.forEach {
                    isAvailable = it.dateTime.toLocalDate().isBefore(chartSelectedWeeksFirstDay)
                    if (isAvailable)
                        return@forEach
                }
                isAvailable
            }
            ChartType.YEAR -> {
                var isAvailable = false
                allDrinks.forEach {
                    isAvailable = Year.of(it.dateTime.year).isBefore(chartSelectedYear)
                    if (isAvailable)
                        return@forEach
                }
                isAvailable
            }
        }
    }
    
}