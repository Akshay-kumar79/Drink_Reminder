package com.akshaw.drinkreminder.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.akshaw.drinkreminder.core.data.local.entity.DrinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {

    @Insert
    suspend fun insert(drink: DrinkEntity): Long

    @Query("select * from drinks_table order by milli desc")
    fun getAllDrinks(): Flow<List<DrinkEntity>>

    @Delete
    suspend fun removeDrink(drink: DrinkEntity)

    @Query("delete from drinks_table")
    suspend fun clear()

}