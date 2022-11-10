package com.akshaw.drinkreminder.feature_onboarding.presentation

import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class OnboardingState(
    val page: OnboardingPage = OnboardingPage.GENDER,
    val gender: Gender = Gender.Female,
    val age: Int = 20,
    val weight: Float = 75.0f,
    val weightUnit: WeightUnit = WeightUnit.KG,
    val bedTimeHour: String = "10",
    val bedTimeMinute: String = "00",
    val bedTimeUnit: TimeUnit = TimeUnit.PM,
    val wakeupTimeHour: String = "07",
    val wakeupTimeMinute: String = "00",
    val wakeupTimeUnit: TimeUnit = TimeUnit.AM
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