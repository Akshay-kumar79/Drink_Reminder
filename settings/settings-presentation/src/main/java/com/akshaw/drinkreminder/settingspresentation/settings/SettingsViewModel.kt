package com.akshaw.drinkreminder.settingspresentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    preferences: Preferences
): ViewModel() {
    
    
    /** General Settings States */
    // Unit
    private val _isChangeUnitDialogShowing = MutableStateFlow(false)
    val isChangeUnitDialogShowing = _isChangeUnitDialogShowing.asStateFlow()
    
    
    val currentWaterUnit = preferences.getWaterUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WATER_UNIT)
    
    
    val currentWeightUnit = preferences.getWeightUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WEIGHT_UNIT)
    
    
    // Daily Intake Goal
    private val _isChangeDailyGoalDialogShowing = MutableStateFlow(false)
    val isChangeDailyGoalDialogShowing = _isChangeDailyGoalDialogShowing.asStateFlow()
    
    
    val dailyIntakeGoal = preferences.getDailyWaterIntakeGoal().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL)
    
    
    /** Personal Information States */
    // Gender
    private val _isChangeGenderDialogShowing = MutableStateFlow(false)
    val isChangeGenderDialogShowing = _isChangeGenderDialogShowing.asStateFlow()
    
    
    val currentGender = preferences.getGender().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_GENDER)
    
    
    // Age
    private val _isChangeAgeDialogShowing = MutableStateFlow(false)
    val isChangeAgeDialogShowing = _isChangeAgeDialogShowing.asStateFlow()
    
    
    val currentAge = preferences.getAge().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_AGE)
    
    
    // Weight
    private val _isChangeWeightDialogShowing = MutableStateFlow(false)
    val isChangeWeightDialogShowing = _isChangeWeightDialogShowing.asStateFlow()
    
    
    val currentWeight = preferences.getWeight().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WEIGHT)
    
    
    // Bed time
    private val _isChangeBedTimeDialogShowing = MutableStateFlow(false)
    val isChangeBedTimeDialogShowing = _isChangeBedTimeDialogShowing.asStateFlow()
    
    
    val currentBedTime = preferences.getBedTime().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LocalTime.now())
    
    
    // Wakeup time
    private val _isChangeWakeUpTimeDialogShowing = MutableStateFlow(false)
    val isChangeWakeUpTimeDialogShowing = _isChangeWakeUpTimeDialogShowing.asStateFlow()
    
    
    val currentWakeUpTime = preferences.getWakeupTime().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LocalTime.now())
    
    
    
    
}