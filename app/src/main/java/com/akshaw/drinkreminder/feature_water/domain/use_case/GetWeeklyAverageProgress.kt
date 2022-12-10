package com.akshaw.drinkreminder.feature_water.domain.use_case

import android.util.Log
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import java.time.LocalDate
import javax.inject.Inject

class GetWeeklyAverageProgress @Inject constructor(
    private val getDrinkProgress: GetDrinkProgress,
    private val filterADayDrinks: FilterADayDrinks
) {
    
    operator fun invoke(allDrink: List<Drink>): Double{
        var totalAWeekProgress = 0.0
        val weekLastDay = LocalDate.now()
        val weekFirstDay = weekLastDay.minusWeeks(1).plusDays(1)
        
        var iteratingDay = weekFirstDay
        while (!iteratingDay.isEqual(weekLastDay.plusDays(1))) {
            totalAWeekProgress += getDrinkProgress(filterADayDrinks(iteratingDay, allDrink))
            iteratingDay = iteratingDay.plusDays(1)
        }
        return totalAWeekProgress/7
    }
    
}