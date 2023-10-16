package com.akshaw.drinkreminder.core.data.repository

import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.domain.mapper.toDrink
import com.akshaw.drinkreminder.core.domain.mapper.toDrinkEntity
import com.akshaw.drinkreminder.core.domain.mapper.toTrackableDrink
import com.akshaw.drinkreminder.core.domain.mapper.toTrackableDrinkEntity
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterRepositoryImpl @Inject constructor(
    database: MyDatabase
) : WaterRepository {
    
    private val drinkDao = database.drinkDao
    private val trackableDrinkDao = database.trackableDrinkDao
    
    
    override suspend fun insertDrink(drink: Drink): Long {
        return drinkDao.insert(drink.toDrinkEntity())
    }
    
    override fun getAllDrink(): Flow<List<Drink>> {
        return drinkDao.getAllDrinks().map {
            it.map { drinkEntity ->
                drinkEntity.toDrink()
            }
        }
    }
    
    override suspend fun removeDrink(drink: Drink) {
        drinkDao.removeDrink(drink.toDrinkEntity())
    }
    
    override suspend fun clearDrink() {
        drinkDao.clear()
    }
    
    
    override suspend fun insertTrackableDrink(trackableDrink: TrackableDrink): Long {
        return trackableDrinkDao.insert(trackableDrink.toTrackableDrinkEntity())
    }
    
    override fun getAllTrackableDrinks(): Flow<List<TrackableDrink>> {
        return trackableDrinkDao.getAllTrackableDrinks().map {
            it.map { trackableDrinkEntity ->
                trackableDrinkEntity.toTrackableDrink()
            }
        }
    }
    
    override suspend fun removeTrackableDrink(trackableDrink: TrackableDrink) {
        trackableDrinkDao.removeTrackableDrink(trackableDrink.toTrackableDrinkEntity())
    }
    
    override suspend fun clearTrackableDrink() {
        trackableDrinkDao.clear()
    }
    
}