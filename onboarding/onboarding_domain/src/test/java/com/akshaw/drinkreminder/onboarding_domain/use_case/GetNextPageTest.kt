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

class GetNextPageTest {
    
    private lateinit var getNextPage: GetNextPage
    
    @BeforeEach
    fun setUp() {
        getNextPage = GetNextPage()
    }
    
    @ParameterizedTest
    @MethodSource("getNextPageSuccessArgs")
    fun `getNextPage with favourable success input, returns success with expected next page`(
        inputPage: OnboardingPage,
        hasAllPermission: Boolean,
        expectedNextPage: OnboardingPage
    ) {
        val result = getNextPage(inputPage, hasAllPermission)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(expectedNextPage)
    }
    
    @ParameterizedTest
    @MethodSource("getNextPageFailureArgs")
    fun `getNextPage with favourable failure input, returns failure`(
        inputPage: OnboardingPage,
        hasAllPermission: Boolean
    ) {
        val result = getNextPage(inputPage, hasAllPermission)
        
        assertThat(result.isFailure).isTrue()
    }
    
    
    companion object {
        @JvmStatic
        fun getNextPageSuccessArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(OnboardingPage.GENDER, true, OnboardingPage.AGE),
            Arguments.arguments(OnboardingPage.AGE, true, OnboardingPage.WEIGHT),
            Arguments.arguments(OnboardingPage.WEIGHT, true, OnboardingPage.BED_TIME),
            Arguments.arguments(OnboardingPage.BED_TIME, true, OnboardingPage.WAKEUP_TIME),
            Arguments.arguments(OnboardingPage.GENDER, false, OnboardingPage.AGE),
            Arguments.arguments(OnboardingPage.AGE, false, OnboardingPage.WEIGHT),
            Arguments.arguments(OnboardingPage.WEIGHT, false, OnboardingPage.BED_TIME),
            Arguments.arguments(OnboardingPage.BED_TIME, false, OnboardingPage.WAKEUP_TIME),
            Arguments.arguments(OnboardingPage.WAKEUP_TIME, false, OnboardingPage.PERMISSION),
        )
        
        @JvmStatic
        fun getNextPageFailureArgs(): Stream<Arguments> = Stream.of(
            Arguments.arguments(OnboardingPage.WAKEUP_TIME, true),
            Arguments.arguments(OnboardingPage.PERMISSION, true),
            Arguments.arguments(OnboardingPage.PERMISSION, false)
        )
    }
    
}