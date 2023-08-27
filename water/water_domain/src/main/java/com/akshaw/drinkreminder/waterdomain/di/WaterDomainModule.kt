package com.akshaw.drinkreminder.waterdomain.di

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import com.akshaw.drinkreminder.waterdomain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterDomainModule {
    
    @Provides
    @Singleton
    fun providesGetDrinkProgress(preferences: Preferences): GetDrinkProgress {
        return GetDrinkProgress()
    }
    
    @Provides
    @Singleton
    fun providesGetTrackableDrinks(): FilterTrackableDrinksByUnit {
        return FilterTrackableDrinksByUnit()
    }
    
    @Provides
    @Singleton
    fun providesGetSelectedTrackableDrinkId(preferences: Preferences): GetSelectedTrackableDrink {
        return GetSelectedTrackableDrink(preferences)
    }
    
    @Provides
    @Singleton
    fun providesDrinkNow(preferences: Preferences, addDrink: AddDrink): DrinkNow {
        return DrinkNow(preferences, addDrink)
    }
    
    @Provides
    @Singleton
    fun provideValidateQuantity(preferences: Preferences, filterOutDigits: FilterOutDigits): ValidateQuantity {
        return ValidateQuantity(preferences, filterOutDigits)
    }
    
    @Provides
    @Singleton
    fun provideAddDrink(waterRepository: WaterRepository): AddDrink {
        return AddDrink(waterRepository)
    }
    
    @Provides
    @Singleton
    fun provideAddTrackableDrink(waterRepository: WaterRepository): AddTrackableDrink {
        return AddTrackableDrink(waterRepository)
    }
    
    @Provides
    @Singleton
    fun provideDeleteDrink(waterRepository: WaterRepository): DeleteDrink {
        return DeleteDrink(waterRepository)
    }
    
    @Provides
    @Singleton
    fun provideDeleteTrackableDrink(waterRepository: WaterRepository): DeleteTrackableDrink {
        return DeleteTrackableDrink(waterRepository)
    }
    
    @Provides
    @Singleton
    fun provideFilterADayDrinks(): FilterADayDrinks {
        return FilterADayDrinks()
    }
    
    @Provides
    @Singleton
    fun provideFilterAMonthDrinks(): FilterAMonthDrink {
        return FilterAMonthDrink()
    }
    
    @Provides
    @Singleton
    fun provideIsReportChartLeftAvailable(): IsReportChartLeftAvailable {
        return IsReportChartLeftAvailable()
    }
    
    @Provides
    @Singleton
    fun provideIsReportChartRightAvailable(): IsReportChartRightAvailable {
        return IsReportChartRightAvailable()
    }
    
    
}