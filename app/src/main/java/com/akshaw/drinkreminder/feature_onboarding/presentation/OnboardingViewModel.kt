package com.akshaw.drinkreminder.feature_onboarding.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: Preferences,
    private val getLocalTime: GetLocalTime
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
                preferences.saveWeight(state.weight)
                preferences.saveWeightUnit(state.weightUnit)
                navigateToNextPage()
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
        if (state.page.nextPage() != null) {
            state = state.copy(
                page = state.page.nextPage()!!
            )
        } else {
            _uiEvent.send(UiEvent.Success)
        }
    }

    private fun navigateToPreviousPage(){
        if (state.page.previousPage() != null){
            state = state.copy(
                page = state.page.previousPage()!!
            )
        }
    }

    private suspend fun triggerErrorEvent() {
        _uiEvent.send(
            UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong))
        )
    }

}