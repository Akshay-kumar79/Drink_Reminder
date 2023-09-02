package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.YearMonth

/**
 * returns list of drinks on provided yearMonth after filtering allDrinks
 */
class FilterAMonthDrink {
    
    operator fun invoke(yearMonth: YearMonth, allDrinks: List<Drink>): List<Drink> {
        
        return allDrinks.filter { drink ->
            YearMonth.from(drink.dateTime).equals(yearMonth)
        }
    }
    
}