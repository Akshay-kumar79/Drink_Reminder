package com.akshaw.drinkreminder.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.akshaw.drinkreminder.core.data.local.dao.DrinkDao
import com.akshaw.drinkreminder.core.data.local.dao.DrinkReminderDao
import com.akshaw.drinkreminder.core.data.local.dao.TrackableDrinkDao
import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity
import com.akshaw.drinkreminder.core.data.local.entity.DrinkReminderEntity
import com.akshaw.drinkreminder.core.data.local.entity.TrackableDrinkEntity

@Database(
    entities = [DrinkEntity::class, TrackableDrinkEntity::class, DrinkReminderEntity::class],
    version = 2
)
abstract class MyDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao
    abstract val trackableDrinkDao: TrackableDrinkDao
    abstract val drinkReminderDao: DrinkReminderDao
    
    companion object {
        const val DATABASE_NAME = "my_database"
        
        // Changed DrinkEntity.id type from Long? to Long and added default to 0
        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS DrinkEntityTmp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, milli INTEGER NOT NULL, waterIntake REAL NOT NULL, unit TEXT NOT NULL)"
                )
                
                // Copy the data
                database.execSQL("INSERT INTO DrinkEntityTmp (id, milli, waterIntake, unit) SELECT id, milli, waterIntake, unit FROM drinks_table")
                
                // Remove the old table
                database.execSQL("DROP TABLE drinks_table")
                
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE DrinkEntityTmp RENAME TO drinks_table")
            }
        }
    }
}