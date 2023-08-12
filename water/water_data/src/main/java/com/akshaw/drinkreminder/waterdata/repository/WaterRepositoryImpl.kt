package com.akshaw.drinkreminder.waterdata.repository

import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.waterdomain.mapper.toDrink
import com.akshaw.drinkreminder.waterdomain.mapper.toDrinkEntity
import com.akshaw.drinkreminder.waterdomain.mapper.toTrackableDrink
import com.akshaw.drinkreminder.waterdomain.mapper.toTrackableDrinkEntity
import com.akshaw.drinkreminder.waterdomain.model.Drink
import com.akshaw.drinkreminder.waterdomain.model.TrackableDrink
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterRepositoryImpl @Inject constructor(
    private val database: MyDatabase
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