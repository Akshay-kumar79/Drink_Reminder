package com.akshaw.drinkreminder.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.data.preferences.DefaultPreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreference(
        sharedPref: SharedPreferences
    ): Preferences {
        return DefaultPreference(sharedPref)
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
    fun provideFilterOutDigits(): FilterOutDigits{
        return FilterOutDigits()
    }

}