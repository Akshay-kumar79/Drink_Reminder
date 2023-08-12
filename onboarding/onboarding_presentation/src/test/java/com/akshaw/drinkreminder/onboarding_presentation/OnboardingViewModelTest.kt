package com.akshaw.drinkreminder.onboarding_presentation

import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.onboarding_domain.use_case.GetNextPage
import com.akshaw.drinkreminder.onboarding_domain.use_case.GetPreviousPage
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