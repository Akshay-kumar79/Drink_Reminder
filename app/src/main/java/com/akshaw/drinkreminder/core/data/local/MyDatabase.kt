package com.akshaw.drinkreminder.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akshaw.drinkreminder.core.data.local.dao.DrinkDao
import com.akshaw.drinkreminder.core.data.local.dao.TrackableDrinkDao
import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity
import com.akshaw.drinkreminder.core.data.local.entity.TrackableDrinkEntity

@Database(
    entities = [DrinkEntity::class, TrackableDrinkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao
    abstract val trackableDrinkDao: TrackableDrinkDao

    companion object {
        const val DATABASE_NAME = "my_database"
    }
}