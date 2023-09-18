package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.waterdomain.utils.ChartType
import java.time.LocalDate
import java.time.Year

/**
 *  returns
 *
 *  true,
 *      -> any drinks available before current year and selected chart is YEAR
 *      -> any drink is available before current week and selected chart is WEEK
 *
 *  false,
 *      -> no drink is available before current year and selected chart is YEAR
 *      -> no drink is available before current week and selected chart is WEEK
 */
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
                for (drink in allDrinks){
                    isAvailable = drink.dateTime.toLocalDate().isBefore(chartSelectedWeeksFirstDay)
                    if (isAvailable)
                        break
                }
                isAvailable
            }
            ChartType.YEAR -> {
                var isAvailable = false
                for (drink in allDrinks){
                    isAvailable = Year.of(drink.dateTime.year).isBefore(chartSelectedYear)
                    if (isAvailable)
                        break
                }
                isAvailable
            }
        }
    }
    
}