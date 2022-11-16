package com.akshaw.drinkreminder.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akshaw.drinkreminder.core.data.local.dao.DrinkDao
import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity

@Database(
    entities = [DrinkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao

    companion object {
        const val DATABASE_NAME = "my_database"
    }
}