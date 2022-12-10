package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.ceil

class GetWeeklyAverageFrequency @Inject constructor(
    private val filterADayDrinks: FilterADayDrinks
) {
    
    operator fun invoke(allDrinks: List<Drink>): Int{
        var totalDrinks = 0
        val weekLastDay = LocalDate.now()
        val weekFirstDay = weekLastDay.minusWeeks(1).plusDays(1)
    
        var iteratingDay = weekFirstDay
        while (!iteratingDay.isEqual(weekLastDay.plusDays(1))) {
            totalDrinks += filterADayDrinks(iteratingDay, allDrinks).size
            iteratingDay = iteratingDay.plusDays(1)
        }
        return ceil(totalDrinks / 7.0).toInt()
    }
    
}