package com.akshaw.drinkreminder.waterdata.di

import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.waterdata.repository.WaterRepositoryImpl
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import com.akshaw.drinkreminder.waterdomain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterDataModule {
    
    @Provides
    @Singleton
    fun providesDrinkWaterRepository(database: MyDatabase): WaterRepository {
        return WaterRepositoryImpl(database)
    }
}