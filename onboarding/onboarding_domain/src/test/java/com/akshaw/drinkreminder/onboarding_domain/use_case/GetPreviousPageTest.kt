package com.akshaw.drinkreminder.onboarding_domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPreviousPageTest {

    private lateinit var getPreviousPage: GetPreviousPage

    @BeforeEach
    fun setUp() {
        getPreviousPage = GetPreviousPage()
    }

    @Test
    fun `Gender page, return failure`(){
        val result = getPreviousPage(OnboardingPage.GENDER)
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `Age page, return success with Gender page`(){
        val result = getPreviousPage(OnboardingPage.AGE)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.GENDER)
    }

    @Test
    fun `Weight page, return success with Age page`(){
        val result = getPreviousPage(OnboardingPage.WEIGHT)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.AGE)
    }

    @Test
    fun `BedTime page, return success with Weight page`(){
        val result = getPreviousPage(OnboardingPage.BED_TIME)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.WEIGHT)
    }

    @Test
    fun `wakeupTime page, return success with BedTime page`(){
        val result = getPreviousPage(OnboardingPage.WAKEUP_TIME)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(OnboardingPage.BED_TIME)
    }

}