package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import java.time.LocalDate

class FilterADayDrinks {
    
    operator fun invoke(date: LocalDate, allDrinks: List<Drink>): List<Drink> {
        
        return allDrinks.filter { drink ->
            drink.dateTime.toLocalDate().equals(date)
        }
    }
    
}