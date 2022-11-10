package com.akshaw.drinkreminder.feature_onboarding.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.feature_onboarding.domain.use_case.GetLocalTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnboardingViewModel @Inject constructor(
    val preferences: Preferences,
    val getLocalTime: GetLocalTime
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
        }
    }

    private fun onNextClick() = viewModelScope.launch {
        when (state.page) {
            OnboardingPage.GENDER -> {
                preferences.saveGender(state.gender)
                state = state.copy(
                    page = OnboardingPage.AGE
                )
            }

            OnboardingPage.AGE -> {
                preferences.saveAge(state.age)
                state = state.copy(
                    page = OnboardingPage.WEIGHT
                )
            }

            OnboardingPage.WEIGHT -> {
                preferences.saveWeight(state.weight)
                preferences.saveWeightUnit(state.weightUnit)
                state = state.copy(
                    page = OnboardingPage.BED_TIME
                )
            }

            OnboardingPage.BED_TIME -> {
                getLocalTime(state.bedTimeHour, state.bedTimeMinute, state.bedTimeUnit)
                    .onSuccess { time ->
                        preferences.saveBedTime(time)
                        state = state.copy(
                            page = OnboardingPage.WAKEUP_TIME
                        )
                    }
                    .onFailure {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong))
                        )
                    }
            }

            OnboardingPage.WAKEUP_TIME -> {
                getLocalTime(state.wakeupTimeHour, state.wakeupTimeMinute, state.wakeupTimeUnit)
                    .onSuccess { time ->
                        preferences.saveWakeupTime(time)
                        _uiEvent.send(UiEvent.Success)
                    }
                    .onFailure {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong))
                        )
                    }
            }
        }
    }

}