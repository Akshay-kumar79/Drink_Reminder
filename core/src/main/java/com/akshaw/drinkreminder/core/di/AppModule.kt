package com.akshaw.drinkreminder.core.di

import android.app.Application
import androidx.room.Room
import com.akshaw.drinkreminder.core.data.MyAndroidNotificationManagerImpl
import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.data.preferences.DefaultPreference
import com.akshaw.drinkreminder.core.data.preferences.dataStore
import com.akshaw.drinkreminder.core.data.repository.ReminderRepositoryImpl
import com.akshaw.drinkreminder.core.data.repository.ReminderSchedulerImpl
import com.akshaw.drinkreminder.core.data.repository.WaterRepositoryImpl
import com.akshaw.drinkreminder.core.domain.MyNotificationManager
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.repository.ReminderRepository
import com.akshaw.drinkreminder.core.domain.repository.ReminderScheduler
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun providePreference(
        app: Application
    ): Preferences {
        return DefaultPreference(app.dataStore)
    }
    
    @Provides
    @Singleton
    fun provideMyNotificationManager(app: Application): MyNotificationManager {
        return MyAndroidNotificationManagerImpl(app)
    }
    
    @Provides
    @Singleton
    fun provideMyDatabase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            MyDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideGetLocalTime(): GetLocalTime {
        return GetLocalTime()
    }
    
    @Provides
    @Singleton
    fun provideFilterOutDigits(): FilterOutDigits {
        return FilterOutDigits()
    }
    
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