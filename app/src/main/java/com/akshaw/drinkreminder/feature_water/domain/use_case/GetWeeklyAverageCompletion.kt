package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import java.time.LocalDate
import javax.inject.Inject

class GetWeeklyAverageCompletion @Inject constructor(
    private val filterADayDrinks: FilterADayDrinks,
    private val getDrinkProgress: GetDrinkProgress
) {
    
    operator fun invoke(allDrinks: List<Drink>, goal: Double): Double{
        var totalCompletion = 0.0
        val weekLastDay = LocalDate.now()
        val weekFirstDay = weekLastDay.minusWeeks(1).plusDays(1)
    
        var iteratingDay = weekFirstDay
        while (!iteratingDay.isEqual(weekLastDay.plusDays(1))) {
            if (getDrinkProgress(filterADayDrinks(iteratingDay, allDrinks)) >= goal){
                totalCompletion++
            }
            iteratingDay = iteratingDay.plusDays(1)
        }
        return totalCompletion * 100.0 / 7.0
    }
    
}