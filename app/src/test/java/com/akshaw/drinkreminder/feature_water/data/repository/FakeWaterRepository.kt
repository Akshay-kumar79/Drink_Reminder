package com.akshaw.drinkreminder.feature_water.data.repository

import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWaterRepository : WaterRepository {

    private val drinks = mutableListOf<Drink>()

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

    override suspend fun clear() {
        drinks.clear()
    }

}