package com.akshaw.drinkreminder.waterdata.di

import android.app.Application
import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.waterdata.repository.ReminderRepositoryImpl
import com.akshaw.drinkreminder.waterdata.repository.ReminderSchedulerImpl
import com.akshaw.drinkreminder.waterdata.repository.WaterRepositoryImpl
import com.akshaw.drinkreminder.waterdomain.repository.ReminderRepository
import com.akshaw.drinkreminder.waterdomain.repository.ReminderScheduler
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
    fun providesWaterRepository(database: MyDatabase): WaterRepository {
        return WaterRepositoryImpl(database)
    }
    
    @Provides
    @Singleton
    fun providesReminderRepository(database: MyDatabase): ReminderRepository {
        return ReminderRepositoryImpl(database)
    }
    
    @Provides
    @Singleton
    fun providesReminderScheduler(application: Application): ReminderScheduler {
        return ReminderSchedulerImpl(application)
    }
}