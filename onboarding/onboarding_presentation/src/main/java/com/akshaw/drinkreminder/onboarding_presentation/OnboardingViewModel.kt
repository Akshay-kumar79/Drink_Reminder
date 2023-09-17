package com.akshaw.drinkreminder.onboarding_presentation

import android.Manifest
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
import com.akshaw.drinkreminder.onboarding_domain.use_case.SaveInitialReminders
import com.akshaw.drinkreminder.onboarding_domain.use_case.SaveInitialDailyIntakeGoal
import com.akshaw.drinkreminder.onboarding_domain.use_case.SaveInitialTrackableDrinks
import com.akshaw.drinkreminder.corecompose.events.NotificationPermissionDialogEvent
import com.akshaw.drinkreminder.onboarding_presentation.events.OnboardingEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: Preferences,
    private val getLocalTime: GetLocalTime,
    private val getNextPage: GetNextPage,
    private val getPreviousPage: GetPreviousPage,
    private val saveInitialReminders: SaveInitialReminders,
    private val saveInitialTrackableDrinks: SaveInitialTrackableDrinks,
    private val saveInitialDailyIntakeGoal: SaveInitialDailyIntakeGoal
) : ViewModel() {
    
    var state by mutableStateOf(OnboardingState())
        private set
    
    
    /** Permissions */
    private val _hasNotificationPermission = MutableStateFlow(true)
    val hasNotificationPermission = _hasNotificationPermission.asStateFlow()
    
    private val _hasExactAlarmPermission = MutableStateFlow(true)
    val hasExactAlarmPermission = _hasExactAlarmPermission.asStateFlow()
    
    // mutable state of dialog to be shown when notification permission is not granted, for greater than android 13
    private val _isReRequestNotificationPermDialogVisible = MutableStateFlow(false)
    val isReRequestNotificationPermDialogVisible = _isReRequestNotificationPermDialogVisible.asStateFlow()
    
    /** One Time events */
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    
    init {
        // workaround to keep stateflow value updated, if it doesn't have active collector
        viewModelScope.launch { hasNotificationPermission.collect {} }
        viewModelScope.launch { hasExactAlarmPermission.collect {} }
    }
    
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
                    // Save default trackable drink first time
                    saveInitialTrackableDrinks()
                    
                    // Save initial reminders for first time
                    saveInitialReminders()
                    
                    // Save recommended daily water intake goal first time
                    saveInitialDailyIntakeGoal()
                    
                    preferences.saveIsOnboardingCompleted(true)
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
            
            is OnboardingEvent.ChangeHasNotificationPermission -> {
                _hasNotificationPermission.value = event.hasPermission
            }
            
            is OnboardingEvent.ChangeHasExactAlarmPermission -> {
                _hasExactAlarmPermission.value = event.hasPermission
            }
            
            is OnboardingEvent.OnPermissionResult -> {
                if (!event.isGranted) {
                    when (event.permission) {
                        Manifest.permission.POST_NOTIFICATIONS -> {
                            _isReRequestNotificationPermDialogVisible.value = true
                        }
                    }
                } else {
                    when (event.permission) {
                        Manifest.permission.POST_NOTIFICATIONS -> {
                            _hasNotificationPermission.value = true
                        }
                    }
                }
            }
        }
    }
    
    fun onEvent(event: NotificationPermissionDialogEvent) {
        when (event) {
            NotificationPermissionDialogEvent.ShowDialog -> {
                _isReRequestNotificationPermDialogVisible.value = true
            }
            
            NotificationPermissionDialogEvent.DismissDialog -> {
                _isReRequestNotificationPermDialogVisible.value = false
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
            
            OnboardingPage.PERMISSION -> {
                navigateToNextPage()
            }
        }
    }
    
    private suspend fun navigateToNextPage() {
        getNextPage(state.page, hasNotificationPermission.value && hasExactAlarmPermission.value)
            .onSuccess { nextPage ->
                // Successfully get next page
                state = state.copy(
                    page = nextPage
                )
            }
            .onFailure {
                // No next page onboarding done now
                
                // Save default trackable drink first time
                saveInitialTrackableDrinks()
                
                // Save initial reminders for first time
                saveInitialReminders()
                
                // Save recommended daily water intake goal first time
                saveInitialDailyIntakeGoal()
                
                preferences.saveIsOnboardingCompleted(true)
                _uiEvent.send(UiEvent.Success)
                
            }
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