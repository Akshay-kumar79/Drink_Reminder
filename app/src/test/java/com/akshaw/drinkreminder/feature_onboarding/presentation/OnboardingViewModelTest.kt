package com.akshaw.drinkreminder.feature_onboarding.presentation

import com.akshaw.drinkreminder.core.data.preferences.FakePreference
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.feature_onboarding.domain.use_case.GetNextPage
import com.akshaw.drinkreminder.feature_onboarding.domain.use_case.GetPreviousPage
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setUp() {
        viewModel = OnboardingViewModel(
            preferences = FakePreference(),
            getLocalTime = GetLocalTime(),
            getNextPage = GetNextPage(),
            getPreviousPage = GetPreviousPage()
        )
    }

    @Test
    fun `onboarding`(){

    }

}