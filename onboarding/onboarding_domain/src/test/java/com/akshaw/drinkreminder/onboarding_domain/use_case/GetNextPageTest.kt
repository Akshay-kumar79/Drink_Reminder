package com.akshaw.drinkreminder.onboarding_domain.use_case

import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class GetNextPageTest{

    private lateinit var getNextPage: GetNextPage

    @Before
    fun setUp(){
        getNextPage = GetNextPage()
    }

    @Test
    fun `Gender page, returns success with age page`(){
        val result = getNextPage(OnboardingPage.GENDER)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.AGE)
    }

    @Test
    fun `Age page, returns success with weight page`(){
        val result = getNextPage(OnboardingPage.AGE)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.WEIGHT)
    }

    @Test
    fun `Weight page, returns success with BedTime page`(){
        val result = getNextPage(OnboardingPage.WEIGHT)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.BED_TIME)
    }

    @Test
    fun `BedTime page, returns success with WakeupTime page`(){
        val result = getNextPage(OnboardingPage.BED_TIME)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.WAKEUP_TIME)
    }

    @Test
    fun `WakeUpTime page, returns failure`(){
        val result = getNextPage(OnboardingPage.WAKEUP_TIME)
        assertThat(result.isFailure).isTrue()
    }

}