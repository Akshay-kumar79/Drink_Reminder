package com.akshaw.drinkreminder.onboarding_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.onboarding_domain.use_case.GetNextPage
import com.akshaw.drinkreminder.onboarding_domain.use_case.GetPreviousPage
import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.akshaw.drinkreminder.core.R

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: Preferences,
    private val getLocalTime: GetLocalTime,
    private val getNextPage: GetNextPage,
    private val getPreviousPage: GetPreviousPage
) : ViewModel() {

    var state by mutableStateOf(OnboardingState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.OnAgeChange -> {
                state = state.copy(
                    age = event.age
                )
            }
            is OnboardingEvent.OnBedTimeHourChange -> {
                state = state.copy(
                    bedTimeHour = event.hour
                )
            }
            is OnboardingEvent.OnBedTimeMinuteChange -> {
                state = state.copy(
                    bedTimeMinute = event.minute
                )
            }
            is OnboardingEvent.OnBedTimeUnitChange -> {
                state = state.copy(
                    bedTimeUnit = event.unit
                )
            }
            is OnboardingEvent.OnGenderClick -> {
                state = state.copy(
                    gender = event.gender
                )
            }
            OnboardingEvent.OnNextClick -> {
                onNextClick()
            }
            OnboardingEvent.OnSkipClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Success)
                }
            }
            is OnboardingEvent.OnWakeupTimeHourChange -> {
                state = state.copy(
                    wakeupTimeHour = event.hour
                )
            }
            is OnboardingEvent.OnWakeupTimeMinuteChange -> {
                state = state.copy(
                    wakeupTimeMinute = event.minute
                )
            }
            is OnboardingEvent.OnWakeupTimeUnitChange -> {
                state = state.copy(
                    wakeupTimeUnit = event.unit
                )
            }
            is OnboardingEvent.OnWeightChange -> {
                state = state.copy(
                    weight = event.weight.toFloat()
                )
            }
            is OnboardingEvent.OnWeightUnitChange -> {
                state = state.copy(
                    weightUnit = event.unit
                )
            }
            OnboardingEvent.OnBackClick -> {
                navigateToPreviousPage()
            }
        }
    }

    private fun onNextClick() = viewModelScope.launch {
        when (state.page) {
            OnboardingPage.GENDER -> {
                preferences.saveGender(state.gender)
                navigateToNextPage()
            }

            OnboardingPage.AGE -> {
                preferences.saveAge(state.age)
                navigateToNextPage()
            }

            OnboardingPage.WEIGHT -> {
                if (state.weightUnit != WeightUnit.Invalid) {
                    preferences.saveWeight(state.weight)
                    preferences.saveWeightUnit(state.weightUnit)
                    navigateToNextPage()
                } else {
                    triggerErrorEvent()
                }
            }

            OnboardingPage.BED_TIME -> {
                getLocalTime(state.bedTimeHour, state.bedTimeMinute, state.bedTimeUnit)
                    .onSuccess { time ->
                        preferences.saveBedTime(time)
                        navigateToNextPage()
                    }
                    .onFailure {
                        triggerErrorEvent()
                    }
            }

            OnboardingPage.WAKEUP_TIME -> {
                getLocalTime(state.wakeupTimeHour, state.wakeupTimeMinute, state.wakeupTimeUnit)
                    .onSuccess { time ->
                        preferences.saveWakeupTime(time)
                        navigateToNextPage()
                    }
                    .onFailure {
                        triggerErrorEvent()
                    }
            }
        }
    }

    private suspend fun navigateToNextPage() {
        getNextPage(state.page)
            .onSuccess { nextPage ->
                state = state.copy(
                    page = nextPage
                )
            }
            .onFailure {
                _uiEvent.send(UiEvent.Success)
            }
        
        getNextPage(state.page)
    }

    private fun navigateToPreviousPage() {
        getPreviousPage(state.page)
            .onSuccess { previousPage ->
                state = state.copy(
                    page = previousPage
                )
            }
    }

    private suspend fun triggerErrorEvent() {
        _uiEvent.send(
            UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong))
        )
    }

}