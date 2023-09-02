package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.util.ChartType
import java.time.LocalDate
import java.time.Year

/**
 *  returns
 *
 *  true,
 *      -> any drink is available after current year and selected chart is YEAR
 *      -> any drink is available after current week and selected chart is WEEK
 *
 *  false,
 *      -> no drink is available after current year and selected chart is YEAR
 *      -> no drink is available after current week and selected chart is WEEK
 */
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
                for (drink in allDrinks) {
                    isAvailable = drink.dateTime.toLocalDate().isAfter(lastDayOfWeek)
                            || lastDayOfWeek.isBefore(LocalDate.now())
                    if (isAvailable)
                        break
                }
                isAvailable
            }
            ChartType.YEAR -> {
                var isAvailable = false
                for (drink in allDrinks) {
                    isAvailable = Year.of(drink.dateTime.year).isAfter(chartSelectedYear)
                            || chartSelectedYear.isBefore(Year.now())
                    if (isAvailable)
                        break
                }
                isAvailable
            }
        }
        
    }
}