package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.LocalDate
import javax.inject.Inject

/**
 *  return the average to progress made in last week
 *  i.e (total progress in last week / 7)
 *
 *  if today is 24-Nov-2022 then last week would be considered from 18-Nov-2022 to 24-Nov-2022
 */
class GetWeeklyAverageProgress @Inject constructor(
    private val getDrinkProgress: GetDrinkProgress,
    private val filterADayDrinks: FilterADayDrinks
) {
    
    operator fun invoke(allDrink: List<Drink>, currentWaterUnit: WaterUnit): Double{
        var totalAWeekProgress = 0.0
        val weekLastDay = LocalDate.now()
        val weekFirstDay = weekLastDay.minusWeeks(1).plusDays(1)
        
        var iteratingDay = weekFirstDay
        while (!iteratingDay.isEqual(weekLastDay.plusDays(1))) {
            totalAWeekProgress += getDrinkProgress(filterADayDrinks(iteratingDay, allDrink), currentWaterUnit)
            iteratingDay = iteratingDay.plusDays(1)
        }
        return totalAWeekProgress/7
    }
    
}