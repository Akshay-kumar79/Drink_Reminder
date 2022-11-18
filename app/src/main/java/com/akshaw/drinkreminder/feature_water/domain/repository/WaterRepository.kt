package com.akshaw.drinkreminder.feature_water.domain.repository

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import kotlinx.coroutines.flow.Flow

interface WaterRepository {

    suspend fun insertDrink(drink: Drink): Long
    fun getAllDrink(): Flow<List<Drink>>
    suspend fun removeDrink(drink: Drink)
    suspend fun clearDrink()


    suspend fun insertTrackableDrink(trackableDrink: TrackableDrink): Long
    fun getAllTrackableDrinks() :Flow<List<TrackableDrink>>
    suspend fun removeTrackableDrink(trackableDrink: TrackableDrink)
    suspend fun clearTrackableDrink()

}