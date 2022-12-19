package com.akshaw.drinkreminder.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity
import com.akshaw.drinkreminder.core.data.local.entity.TrackableDrinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackableDrinkDao {

    @Insert
    suspend fun insert(trackableDrink: TrackableDrinkEntity): Long

    @Query("select * from trackable_drinks_table order by amount asc")
    fun getAllTrackableDrinks(): Flow<List<TrackableDrinkEntity>>

    @Delete
    suspend fun removeTrackableDrink(trackableDrink: TrackableDrinkEntity)

    @Query("delete from trackable_drinks_table")
    suspend fun clear()

}