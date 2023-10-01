package com.akshaw.drinkreminder.onboarding_domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class GetPreviousPageTest {
    
    private lateinit var getPreviousPage: GetPreviousPage
    
    @BeforeEach
    fun setUp() {
        getPreviousPage = GetPreviousPage()
    }
    
    @ParameterizedTest
    @MethodSource("getPreviousPageSuccessArgs")
    fun `getPreviousPage with favourable success input, returns success with expected previous page`(
        inputPage: OnboardingPage,
        expectedPreviousPage: OnboardingPage
    ) {
        val result = getPreviousPage(inputPage)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(expectedPreviousPage)
    }
    
    @ParameterizedTest
    @MethodSource("getPreviousPageFailureArgs")
    fun `getPreviousPage with favourable failure input, returns failure`(
        inputPage: OnboardingPage
    ) {
        val result = getPreviousPage(inputPage)
        
        assertThat(result.isFailure).isTrue()
    }
    
    
    companion object {
        @JvmStatic
        fun getPreviousPageSuccessArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(OnboardingPage.PERMISSION, OnboardingPage.WAKEUP_TIME),
            Arguments.arguments(OnboardingPage.WAKEUP_TIME, OnboardingPage.BED_TIME),
            Arguments.arguments(OnboardingPage.BED_TIME, OnboardingPage.WEIGHT),
            Arguments.arguments(OnboardingPage.WEIGHT, OnboardingPage.AGE),
            Arguments.arguments(OnboardingPage.AGE, OnboardingPage.GENDER),
        )
        
        @JvmStatic
        fun getPreviousPageFailureArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(OnboardingPage.GENDER),
        )
    }
    
}