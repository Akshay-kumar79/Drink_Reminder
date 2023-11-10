package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.ceil

private const val DAYS_IN_WEEK = 7.0

/**
 *  returns average number of drinks in last week on daily basis
 *  i.e ceiling of (total number of drink in last week) / 7
 *
 *  if today is 24-Nov-2022 then last week would be considered from 18-Nov-2022 to 24-Nov-2022
 */
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
        return ceil(totalDrinks / DAYS_IN_WEEK).toInt()
    }
    
}