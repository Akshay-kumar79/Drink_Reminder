package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import java.time.YearMonth

/**
 * Filter drinks for one month
 */
class FilterAMonthDrink {
    
    /**
     * @param yearMonth month of which drinks need to be filtered
     * @param allDrinks list of drinks from which drinks will be filtered
     *
     *  @return list of drinks for provided [yearMonth] after filtering [allDrinks]
     */
    operator fun invoke(yearMonth: YearMonth, allDrinks: List<Drink>): List<Drink> {
        
        return allDrinks.filter { drink ->
            YearMonth.from(drink.dateTime).equals(yearMonth)
        }
    }
    
}