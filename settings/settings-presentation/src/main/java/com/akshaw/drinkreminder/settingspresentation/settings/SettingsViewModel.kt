package com.akshaw.drinkreminder.settingspresentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {
    
    
    /** General Settings States */
    // Unit
    private val _isChangeUnitDialogShowing = MutableStateFlow(false)
    val isChangeUnitDialogShowing = _isChangeUnitDialogShowing.asStateFlow()
    
    private val _selectedWaterUnit = MutableStateFlow<WaterUnit>(Constants.DEFAULT_WATER_UNIT)
    val selectedWaterUnit = _selectedWaterUnit.asStateFlow()
    
    private val _selectedWeightUnit = MutableStateFlow<WeightUnit>(Constants.DEFAULT_WEIGHT_UNIT)
    val selectedWeightUnit = _selectedWeightUnit.asStateFlow()
    
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
    
    
    fun showUnitDialog() {
        _selectedWaterUnit.value = currentWaterUnit.value
        _selectedWeightUnit.value = currentWeightUnit.value
        _isChangeUnitDialogShowing.value = true
    }
    
    fun dismissUnitDialog() {
        _isChangeUnitDialogShowing.value = false
    }
    
    fun changeSelectedUnit(waterUnit: WaterUnit? = null, weightUnit: WeightUnit? = null) {
        waterUnit?.let { _selectedWaterUnit.value = it }
        weightUnit?.let { _selectedWeightUnit.value = it }
    }
    
    fun saveNewUnits() {
        _isChangeUnitDialogShowing.value = false
    }
    
}