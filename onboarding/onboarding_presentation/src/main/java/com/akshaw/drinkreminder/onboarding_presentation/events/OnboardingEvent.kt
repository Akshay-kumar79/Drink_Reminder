package com.akshaw.drinkreminder.onboarding_presentation.events

import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.util.WeightUnit

sealed interface OnboardingEvent {
    object OnNextClick : OnboardingEvent
    data class OnGenderClick(val gender: Gender) : OnboardingEvent
    data class OnAgeChange(val age: Int) : OnboardingEvent
    data class OnWeightChange(val weight: Int) : OnboardingEvent
    data class OnWeightUnitChange(val unit: WeightUnit) : OnboardingEvent
    data class OnBedTimeHourChange(val hour: Int) : OnboardingEvent
    data class OnBedTimeMinuteChange(val minute: Int) : OnboardingEvent
    data class OnBedTimeUnitChange(val unit: TimeUnit) : OnboardingEvent
    data class OnWakeupTimeHourChange(val hour: Int) : OnboardingEvent
    data class OnWakeupTimeMinuteChange(val minute: Int) : OnboardingEvent
    data class OnWakeupTimeUnitChange(val unit: TimeUnit) : OnboardingEvent
    object OnBackClick : OnboardingEvent
    object OnSkipClick : OnboardingEvent
    data class ChangeHasNotificationPermission(val hasPermission: Boolean): OnboardingEvent
    data class ChangeHasExactAlarmPermission(val hasPermission: Boolean): OnboardingEvent
    data class OnPermissionResult(val permission: String, val isGranted: Boolean) : OnboardingEvent
}
