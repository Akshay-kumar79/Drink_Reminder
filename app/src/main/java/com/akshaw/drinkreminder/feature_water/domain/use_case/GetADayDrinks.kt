package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetADayDrinks @Inject constructor(
    private val repository: WaterRepository
) {

    operator fun invoke(date: LocalDate): Flow<List<Drink>> =
        repository.getAllDrink().map {
            it.filter { drink ->
                drink.dateTime.toLocalDate().equals(date)
            }
        }


}