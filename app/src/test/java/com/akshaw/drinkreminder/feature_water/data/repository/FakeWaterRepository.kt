package com.akshaw.drinkreminder.feature_water.data.repository

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import io.mockk.internalSubstitute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWaterRepository : WaterRepository {

    private val drinks = mutableListOf<Drink>()
    private val trackableDrinks = mutableListOf<TrackableDrink>()

    override suspend fun insertDrink(drink: Drink): Long {
        drinks.add(drink)
        return drink.id ?: -1
    }

    override fun getAllDrink(): Flow<List<Drink>> {
        return flow {
            emit(drinks)
        }
    }

    override suspend fun removeDrink(drink: Drink) {
        drinks.remove(drink)
    }

    override suspend fun clearDrink() {
        drinks.clear()
    }


    override suspend fun insertTrackableDrink(trackableDrink: TrackableDrink): Long {
        trackableDrinks.add(trackableDrink)
        return trackableDrink.id ?: -1
    }

    override fun getAllTrackableDrinks(): Flow<List<TrackableDrink>> {
        return flow {
            emit(trackableDrinks)
        }
    }

    override suspend fun removeTrackableDrink(trackableDrink: TrackableDrink) {
        trackableDrinks.remove(trackableDrink)
    }

    override suspend fun clearTrackableDrink() {
        trackableDrinks.clear()
    }
}