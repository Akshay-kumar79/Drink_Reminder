package com.akshaw.drinkreminder.feature_onboarding.di

import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.feature_onboarding.domain.use_case.GetNextPage
import com.akshaw.drinkreminder.feature_onboarding.domain.use_case.GetPreviousPage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingModule {

    @Provides
    @ViewModelScoped
    fun provideGetNextPage(): GetNextPage{
        return GetNextPage()
    }

    @Provides
    @ViewModelScoped
    fun provideGetPreviousPage(): GetPreviousPage{
        return GetPreviousPage()
    }

}