package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.floor

/**
 * returns
-> TrackableDrink with id -1, when there is 0 trackable drinks
-> TrackableDrink with default selected id, when trackableDrinks contains TrackableDrink with default selected id
-> TrackableDrink with middle indexed trackable drink id, when default selected id does not exist
 */
class GetSelectedTrackableDrink @Inject constructor(
    private val preferences: Preferences
) {

    suspend operator fun invoke(trackableDrinks: List<TrackableDrink>, currentWaterUnit: WaterUnit): TrackableDrink {

        val selectedTrackableDrinkId = preferences.getSelectedTrackableDrinkId().first()

        return trackableDrinks.find {
            it.id == selectedTrackableDrinkId
        }
            ?: createNewTrackableDrink(trackableDrinks, currentWaterUnit)

    }

    private fun createNewTrackableDrink(trackableDrinks: List<TrackableDrink>, currentWaterUnit: WaterUnit): TrackableDrink {
        if (trackableDrinks.isEmpty()) {
            return TrackableDrink(-1, 0.0, currentWaterUnit)
        }
        val index = floor(trackableDrinks.size / 2f).toInt()
        return trackableDrinks[index]
    }

}