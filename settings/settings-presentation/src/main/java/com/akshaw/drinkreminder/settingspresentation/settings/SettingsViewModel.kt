package com.akshaw.drinkreminder.settingspresentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeGenderDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeUnitDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.DailyIntakeGoalDialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {
    
    
    /** General Settings States */
    // Unit
    private val _isChangeUnitDialogShowing = MutableStateFlow(false)
    val isChangeUnitDialogShowing = _isChangeUnitDialogShowing.asStateFlow()
    
    private val _selectedWaterUnit = MutableStateFlow<WaterUnit>(Constants.DEFAULT_WATER_UNIT)
    val selectedWaterUnit = _selectedWaterUnit.asStateFlow()
    
    private val _selectedWeightUnit = MutableStateFlow<WeightUnit>(Constants.DEFAULT_WEIGHT_UNIT)
    val selectedWeightUnit = _selectedWeightUnit.asStateFlow()
    
    private val currentWaterUnit = preferences.getWaterUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WATER_UNIT)
    private val currentWeightUnit = preferences.getWeightUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WEIGHT_UNIT)
    
    
    // Daily Intake Goal
    private val _isChangeDailyGoalDialogShowing = MutableStateFlow(false)
    val isChangeDailyGoalDialogShowing = _isChangeDailyGoalDialogShowing.asStateFlow()
    
    private val _selectedDailyIntakeGoal = MutableStateFlow(Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL)
    val selectedDailyIntakeGoal = _selectedDailyIntakeGoal.asStateFlow()
    
    private val dailyIntakeGoal = preferences.getDailyWaterIntakeGoal().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL)
    
    
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
    
    /** Unit dialog events */
    fun onEvent(event: ChangeUnitDialogEvent) {
        when (event) {
            ChangeUnitDialogEvent.ShowDialog -> {
                _selectedWaterUnit.value = currentWaterUnit.value
                _selectedWeightUnit.value = currentWeightUnit.value
                _isChangeUnitDialogShowing.value = true
            }
            
            ChangeUnitDialogEvent.DismissDialog -> {
                _isChangeUnitDialogShowing.value = false
            }
            
            is ChangeUnitDialogEvent.ChangeSelectedUnit -> {
                event.waterUnit?.let { _selectedWaterUnit.value = it }
                event.weightUnit?.let { _selectedWeightUnit.value = it }
            }
            
            ChangeUnitDialogEvent.SaveNewUnits -> {
                // save units to preference
                _isChangeUnitDialogShowing.value = false
            }
        }
    }
    
    /** Daily intake goal dialog events */
    fun onEvent(event: DailyIntakeGoalDialogEvent) {
        when (event) {
            DailyIntakeGoalDialogEvent.ShowDialog -> {
                _selectedDailyIntakeGoal.value = dailyIntakeGoal.value
                _isChangeDailyGoalDialogShowing.value = true
            }
            
            DailyIntakeGoalDialogEvent.DismissDialog -> {
                _isChangeDailyGoalDialogShowing.value = false
            }
            
            is DailyIntakeGoalDialogEvent.OnDailyIntakeGoalChange -> {
                _selectedDailyIntakeGoal.value = event.newIntake
            }
            
            DailyIntakeGoalDialogEvent.SaveDailyIntakeGoal -> {
                // save daily intake goal to preference
                _isChangeDailyGoalDialogShowing.value = false
            }
        }
    }
    
    /** Gender dialog events */
    fun onEvent(event: ChangeGenderDialogEvent) {
        when (event) {
            ChangeGenderDialogEvent.ShowDialog -> {
                _isChangeGenderDialogShowing.value = true
            }
            ChangeGenderDialogEvent.DismissDialog -> {
                _isChangeGenderDialogShowing.value = false
            }
            is ChangeGenderDialogEvent.SaveNewGender -> {
                // save gender
                viewModelScope.launch {
                    preferences.saveGender(event.gender)
                    _isChangeGenderDialogShowing.value = false
                }
            }
        }
    }
    
    
    
    
}