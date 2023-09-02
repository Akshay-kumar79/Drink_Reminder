package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.LocalDate

/**
 * returns list of drinks on provided date after filtering allDrinks
 */
class FilterADayDrinks {
    
    operator fun invoke(date: LocalDate, allDrinks: List<Drink>): List<Drink> {
        
        return allDrinks.filter { drink ->
            drink.dateTime.toLocalDate().equals(date)
        }
    }
    
}