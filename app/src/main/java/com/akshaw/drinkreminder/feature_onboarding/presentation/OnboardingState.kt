package com.akshaw.drinkreminder.feature_onboarding.presentation

import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class OnboardingState(
    val page: OnboardingPage = OnboardingPage.GENDER,
    val gender: Gender = Constants.DEFAULT_GENDER,
    val age: Int = Constants.DEFAULT_AGE,
    val weight: Float = Constants.DEFAULT_WEIGHT.toFloat(),
    val weightUnit: WeightUnit = Constants.DEFAULT_WEIGHT_UNIT,
    val bedTimeHour: Int = Constants.BED_TIME_DEFAULT_HOUR,
    val bedTimeMinute: Int = Constants.BED_TIME_DEFAULT_MINUTE,
    val bedTimeUnit: TimeUnit = Constants.BED_TIME_DEFAULT_UNIT,
    val wakeupTimeHour: Int = Constants.WAKE_TIME_DEFAULT_HOUR,
    val wakeupTimeMinute: Int = Constants.WAKE_TIME_DEFAULT_MINUTE,
    val wakeupTimeUnit: TimeUnit = Constants.WAKE_TIME_DEFAULT_UNIT
)

enum class OnboardingPage{
    GENDER,
    AGE,
    WEIGHT,
    BED_TIME,
    WAKEUP_TIME
}

fun OnboardingPage.nextPage(): OnboardingPage?{
    return when(this){
        OnboardingPage.GENDER -> OnboardingPage.AGE
        OnboardingPage.AGE -> OnboardingPage.WEIGHT
        OnboardingPage.WEIGHT -> OnboardingPage.BED_TIME
        OnboardingPage.BED_TIME -> OnboardingPage.WAKEUP_TIME
        OnboardingPage.WAKEUP_TIME -> null
    }
}

fun OnboardingPage.previousPage(): OnboardingPage?{
    return when(this){
        OnboardingPage.GENDER -> null
        OnboardingPage.AGE -> OnboardingPage.GENDER
        OnboardingPage.WEIGHT -> OnboardingPage.AGE
        OnboardingPage.BED_TIME -> OnboardingPage.WEIGHT
        OnboardingPage.WAKEUP_TIME -> OnboardingPage.BED_TIME
    }
}