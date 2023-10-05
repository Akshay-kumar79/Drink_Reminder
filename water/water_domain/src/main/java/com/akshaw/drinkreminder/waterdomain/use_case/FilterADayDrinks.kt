package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.LocalDate

/**
 * Filter drinks for one day
 */
class FilterADayDrinks {
    
    /**
     * @param date [LocalDate] of which drinks need to be filtered
     * @param allDrinks list of drinks from which drinks will be filtered
     *
     * @return list of drinks on provided [date] after filtering [allDrinks]
     */
    operator fun invoke(date: LocalDate, allDrinks: List<Drink>): List<Drink> {
        
        return allDrinks.filter { drink ->
            drink.dateTime.toLocalDate().equals(date)
        }
    }
    
}