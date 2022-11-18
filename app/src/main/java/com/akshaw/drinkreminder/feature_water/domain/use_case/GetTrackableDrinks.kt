package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrackableDrinks @Inject constructor(
    private val preferences: Preferences,
    private val repository: WaterRepository
) {

    operator fun invoke(): Flow<List<TrackableDrink>> {
        val waterUnit = preferences.loadWaterUnit()

        return repository.getAllTrackableDrinks().map {
            it.filter { trackableDrink ->
                trackableDrink.unit == waterUnit
            }
        }
    }

}