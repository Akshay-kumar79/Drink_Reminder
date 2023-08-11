package com.akshaw.drinkreminder.waterdomain.repository

import com.akshaw.drinkreminder.waterdomain.model.Drink
import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink
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