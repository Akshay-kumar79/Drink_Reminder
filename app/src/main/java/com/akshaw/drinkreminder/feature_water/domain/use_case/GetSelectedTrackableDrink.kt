package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import javax.inject.Inject
import kotlin.math.floor

/** returns
-> TrackableDrink with id -1, when there is 0 trackable drinks
-> TrackableDrink with default selected id, when trackableDrinks contains TrackableDrink with default selected id
-> TrackableDrink with middle indexed trackable drink id, when default selected id does not exist
 */
class GetSelectedTrackableDrink @Inject constructor(
    private val preferences: Preferences
) {

    operator fun invoke(trackableDrinks: List<TrackableDrink>): TrackableDrink {

        val selectedTrackableDrinkId = preferences.loadSelectedTrackableDrinkId()

        return trackableDrinks.find {
            it.id == selectedTrackableDrinkId
        }
            ?: createNewTrackableDrink(trackableDrinks)

    }

    private fun createNewTrackableDrink(trackableDrinks: List<TrackableDrink>): TrackableDrink {
        if (trackableDrinks.isEmpty()) {
            return TrackableDrink(-1, 0.0, preferences.loadWaterUnit())
        }
        val index = floor(trackableDrinks.size / 2f).toInt()
        return trackableDrinks[index]
    }

}