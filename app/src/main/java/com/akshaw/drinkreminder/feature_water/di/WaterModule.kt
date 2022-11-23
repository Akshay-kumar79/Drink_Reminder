package com.akshaw.drinkreminder.feature_water.di

import com.akshaw.drinkreminder.core.data.local.MyDatabase
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.feature_water.data.repository.WaterRepositoryImpl
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.akshaw.drinkreminder.feature_water.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterModule {

    @Provides
    @Singleton
    fun providesDrinkWaterRepository(database: MyDatabase): WaterRepository {
        return WaterRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun providesGetADayDrinks(repository: WaterRepository): GetADayDrinks {
        return GetADayDrinks(repository)
    }

    @Provides
    @Singleton
    fun providesGetDrinkProgress(preferences: Preferences): GetDrinkProgress {
        return GetDrinkProgress(preferences)
    }

    @Provides
    @Singleton
    fun providesGetTrackableDrinks(preferences: Preferences, waterRepository: WaterRepository): GetTrackableDrinks {
        return GetTrackableDrinks(preferences, waterRepository)
    }

    @Provides
    @Singleton
    fun providesGetSelectedTrackableDrinkId(preferences: Preferences): GetSelectedTrackableDrink{
        return GetSelectedTrackableDrink(preferences)
    }

    @Provides
    @Singleton
    fun providesAddDrink(repository: WaterRepository, preferences: Preferences): DrinkNow{
        return DrinkNow(repository, preferences)
    }

    @Provides
    @Singleton
    fun provideValidateQuantity(preferences: Preferences, filterOutDigits: FilterOutDigits): ValidateQuantity{
        return ValidateQuantity(preferences, filterOutDigits)
    }

}