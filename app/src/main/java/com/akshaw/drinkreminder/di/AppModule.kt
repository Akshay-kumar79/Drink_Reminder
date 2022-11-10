package com.akshaw.drinkreminder.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.akshaw.drinkreminder.core.data.preferences.DefaultPreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
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

}