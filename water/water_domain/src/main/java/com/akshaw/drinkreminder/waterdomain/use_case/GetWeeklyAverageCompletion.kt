package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.model.Drink
import java.time.LocalDate
import javax.inject.Inject

/**
 *  returns the percentage of number of days the daily goal completed in last week
 *  i.e (number of day goal completed in the last week) * 100 / 7
 *
 *  if today is 24-Nov-2022 then last week would be considered from 18-Nov-2022 to 24-Nov-2022
 */
class GetWeeklyAverageCompletion @Inject constructor(
    private val filterADayDrinks: FilterADayDrinks,
    private val getDrinkProgress: GetDrinkProgress
) {
    
    operator fun invoke(allDrinks: List<Drink>, goal: Double, currentWaterUnit: WaterUnit): Double {
        var totalCompletion = 0.0
        val weekLastDay = LocalDate.now()
        val weekFirstDay = weekLastDay.minusWeeks(1).plusDays(1)
        
        var iteratingDay = weekFirstDay
        while (!iteratingDay.isEqual(weekLastDay.plusDays(1))) {
            if (getDrinkProgress(filterADayDrinks(iteratingDay, allDrinks), currentWaterUnit) >= goal) {
                totalCompletion++
            }
            iteratingDay = iteratingDay.plusDays(1)
        }
        return totalCompletion * 100.0 / 7.0
    }
    
}