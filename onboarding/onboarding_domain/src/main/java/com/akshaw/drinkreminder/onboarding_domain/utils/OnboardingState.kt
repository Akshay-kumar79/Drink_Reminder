package com.akshaw.drinkreminder.onboarding_domain.utils

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit

data class OnboardingState(
    val page: OnboardingPage = OnboardingPage.GENDER,
    val gender: Gender = Constants.DEFAULT_GENDER,
    val age: Int = Constants.DEFAULT_AGE,
    val weight: Float = Constants.DEFAULT_WEIGHT,
    val weightUnit: WeightUnit = Constants.DEFAULT_WEIGHT_UNIT,
    val bedTimeHour: Int = Constants.BED_TIME_DEFAULT_HOUR,
    val bedTimeMinute: Int = Constants.BED_TIME_DEFAULT_MINUTE,
    val bedTimeUnit: TimeUnit = Constants.BED_TIME_DEFAULT_UNIT,
    val wakeupTimeHour: Int = Constants.WAKE_TIME_DEFAULT_HOUR,
    val wakeupTimeMinute: Int = Constants.WAKE_TIME_DEFAULT_MINUTE,
    val wakeupTimeUnit: TimeUnit = Constants.WAKE_TIME_DEFAULT_UNIT
)

enum class OnboardingPage {
    GENDER,
    AGE,
    WEIGHT,
    BED_TIME,
    WAKEUP_TIME,
    PERMISSION
}

fun OnboardingPage.nextPage(): OnboardingPage? {
    return when (this) {
        OnboardingPage.GENDER -> OnboardingPage.AGE
        OnboardingPage.AGE -> OnboardingPage.WEIGHT
        OnboardingPage.WEIGHT -> OnboardingPage.BED_TIME
        OnboardingPage.BED_TIME -> OnboardingPage.WAKEUP_TIME
        OnboardingPage.WAKEUP_TIME -> OnboardingPage.PERMISSION
        OnboardingPage.PERMISSION -> null
    }
}

fun OnboardingPage.previousPage(): OnboardingPage? {
    return when (this) {
        OnboardingPage.GENDER -> null
        OnboardingPage.AGE -> OnboardingPage.GENDER
        OnboardingPage.WEIGHT -> OnboardingPage.AGE
        OnboardingPage.BED_TIME -> OnboardingPage.WEIGHT
        OnboardingPage.WAKEUP_TIME -> OnboardingPage.BED_TIME
        OnboardingPage.PERMISSION -> OnboardingPage.WAKEUP_TIME
    }
}