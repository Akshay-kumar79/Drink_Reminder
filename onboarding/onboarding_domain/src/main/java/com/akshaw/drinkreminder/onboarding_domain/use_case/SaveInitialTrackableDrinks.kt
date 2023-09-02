package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.AddTrackableDrink
import com.akshaw.drinkreminder.core.domain.use_case.ChangeWaterQuantityByUnit
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.floor

class SaveInitialTrackableDrinks @Inject constructor(
    private val preferences: Preferences,
    private val addTrackableDrink: AddTrackableDrink,
    private val changeWaterQuantityByUnit: ChangeWaterQuantityByUnit
) {
    
    suspend operator fun invoke() {
    
        if (preferences.getIsOnboardingCompleted().first()) {
            return
        }
        
        Constants.DEFAULT_TRACKABLE_DRINKS.forEach {
            addTrackableDrink(
                TrackableDrink(
                    amount = it,
                    unit = WaterUnit.ML
                )
            )
            
            addTrackableDrink(
                TrackableDrink(
                    amount = floor(changeWaterQuantityByUnit(it, WaterUnit.ML, WaterUnit.FL_OZ).getOrDefault(0.0)),
                    unit = WaterUnit.FL_OZ
                )
            )
        }
    }
    
}