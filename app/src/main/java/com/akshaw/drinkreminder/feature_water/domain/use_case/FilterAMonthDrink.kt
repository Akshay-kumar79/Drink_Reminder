package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

class FilterAMonthDrink {
    
    operator fun invoke(yearMonth: YearMonth, allDrinks: List<Drink>): List<Drink> {
        
        return allDrinks.filter { drink ->
            YearMonth.from(drink.dateTime).equals(yearMonth)
        }
    }
    
}