package com.akshaw.drinkreminder.settingspresentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.domain.use_case.GetRecommendedDailyWaterIntake
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.corecompose.uievents.UiEvent
import com.akshaw.drinkreminder.corecompose.uievents.UiText
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import com.akshaw.drinkreminder.settings_domain.usecase.SaveNewWaterUnit
import com.akshaw.drinkreminder.settings_domain.usecase.SaveNewWeightUnit
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeAgeDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeBedTimeDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeGenderDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeUnitDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeWakeupTimeDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeWeightDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.DailyIntakeGoalDialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

// TODO remove preference from constructor and make use case for each preference methods
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: Preferences,
    private val getLocalTime: GetLocalTime,
    private val getRecommendedDailyWaterIntake: GetRecommendedDailyWaterIntake,
    private val saveNewWaterUnit: SaveNewWaterUnit,
    private val saveNewWeightUnit: SaveNewWeightUnit
) : ViewModel() {
    
    
    /** General Settings States */
    // Unit
    val currentWaterUnit = preferences.getWaterUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WATER_UNIT)
    val currentWeightUnit = preferences.getWeightUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WEIGHT_UNIT)
    
    private val _isChangeUnitDialogShowing = MutableStateFlow(false)
    val isChangeUnitDialogShowing = _isChangeUnitDialogShowing.asStateFlow()
    
    private val _selectedWaterUnit = MutableStateFlow<WaterUnit>(Constants.DEFAULT_WATER_UNIT)
    val selectedWaterUnit = _selectedWaterUnit.asStateFlow()
    
    private val _selectedWeightUnit = MutableStateFlow<WeightUnit>(Constants.DEFAULT_WEIGHT_UNIT)
    val selectedWeightUnit = _selectedWeightUnit.asStateFlow()
    
    
    // Daily Intake Goal
    val dailyIntakeGoal = preferences.getDailyWaterIntakeGoal().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL)
    
    private val _isChangeDailyGoalDialogShowing = MutableStateFlow(false)
    val isChangeDailyGoalDialogShowing = _isChangeDailyGoalDialogShowing.asStateFlow()
    
    private val _selectedDailyIntakeGoal = MutableStateFlow(Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL)
    val selectedDailyIntakeGoal = _selectedDailyIntakeGoal.asStateFlow()
    
    
    /** Personal Information States */
    // Gender
    val currentGender = preferences.getGender().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_GENDER)
    
    private val _isChangeGenderDialogShowing = MutableStateFlow(false)
    val isChangeGenderDialogShowing = _isChangeGenderDialogShowing.asStateFlow()
    
    
    // Age
    val currentAge = preferences.getAge().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_AGE)
    
    private val _isChangeAgeDialogShowing = MutableStateFlow(false)
    val isChangeAgeDialogShowing = _isChangeAgeDialogShowing.asStateFlow()
    
    private val _selectedAge = MutableStateFlow(Constants.DEFAULT_AGE)
    val selectedAge = _selectedAge.asStateFlow()
    
    
    // Weight
    val currentWeight = preferences.getWeight().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WEIGHT)
    
    private val _isChangeWeightDialogShowing = MutableStateFlow(false)
    val isChangeWeightDialogShowing = _isChangeWeightDialogShowing.asStateFlow()
    
    private val _selectedWeight = MutableStateFlow(Constants.DEFAULT_WEIGHT)
    val selectedWeight = _selectedWeight.asStateFlow()
    
    
    // Bed time
    val currentBedTime = preferences.getBedTime().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LocalTime.now())
    
    private val _isChangeBedTimeDialogShowing = MutableStateFlow(false)
    val isChangeBedTimeDialogShowing = _isChangeBedTimeDialogShowing.asStateFlow()
    
    private val _changeBedTimeDialogHour = MutableStateFlow(LocalTime.now().hour)
    val changeBedTimeDialogHour = _changeBedTimeDialogHour.asStateFlow()
    
    private val _changeBedTimeDialogMinute = MutableStateFlow(LocalTime.now().minute)
    val changeBedTimeDialogMinute = _changeBedTimeDialogMinute.asStateFlow()
    
    
    // Wakeup time
    val currentWakeUpTime = preferences.getWakeupTime().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LocalTime.now())
    
    private val _isChangeWakeUpTimeDialogShowing = MutableStateFlow(false)
    val isChangeWakeUpTimeDialogShowing = _isChangeWakeUpTimeDialogShowing.asStateFlow()
    
    private val _changeWakeupTimeDialogHour = MutableStateFlow(LocalTime.now().hour)
    val changeWakeupTimeDialogHour = _changeWakeupTimeDialogHour.asStateFlow()
    
    private val _changeWakeupTimeDialogMinute = MutableStateFlow(LocalTime.now().minute)
    val changeWakeupTimeDialogMinute = _changeWakeupTimeDialogMinute.asStateFlow()
    
    
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    
    
    init {
        /** Workaround to keep all stateflow values updated, because i don't know other ways.
        (if [MutableStateFlow] have active collector than value keeps updated) */
        setOf(
            currentWaterUnit,
            currentWeightUnit,
            dailyIntakeGoal,
            currentGender,
            currentAge,
            currentWeight,
            currentBedTime,
            currentWakeUpTime
        ).forEach {
            viewModelScope.launch {
                it.collect()
            }
        }
    }
    
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
                viewModelScope.launch {
                    saveNewWaterUnit(selectedWaterUnit.value)
                    saveNewWeightUnit(selectedWeightUnit.value)
                    
                    _isChangeUnitDialogShowing.value = false
                }
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
            
            DailyIntakeGoalDialogEvent.OnReset -> {
                getRecommendedDailyWaterIntake(
                    currentAge.value,
                    currentWeight.value,
                    currentWeightUnit.value,
                    currentGender.value,
                    currentWaterUnit.value
                ).onSuccess {
                    _selectedDailyIntakeGoal.value = it.toDouble()
                }.onFailure {
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong)))
                    }
                }
            }
            
            DailyIntakeGoalDialogEvent.SaveDailyIntakeGoal -> {
                // save daily intake goal to preference
                viewModelScope.launch {
                    preferences.saveDailyWaterIntakeGoal(selectedDailyIntakeGoal.value)
                    _isChangeDailyGoalDialogShowing.value = false
                }
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
    
    /** Age dialog events */
    fun onEvent(event: ChangeAgeDialogEvent) {
        when (event) {
            ChangeAgeDialogEvent.ShowDialog -> {
                _selectedAge.value = currentAge.value
                _isChangeAgeDialogShowing.value = true
            }
            
            ChangeAgeDialogEvent.DismissDialog -> {
                _isChangeAgeDialogShowing.value = false
            }
            
            is ChangeAgeDialogEvent.OnAgeChange -> {
                _selectedAge.value = event.newAge
            }
            
            ChangeAgeDialogEvent.SaveNewAge -> {
                // save age
                viewModelScope.launch {
                    preferences.saveAge(selectedAge.value)
                    _isChangeAgeDialogShowing.value = false
                }
            }
        }
    }
    
    /** Weight dialog events */
    fun onEvent(event: ChangeWeightDialogEvent) {
        when (event) {
            ChangeWeightDialogEvent.ShowDialog -> {
                _selectedWeight.value = currentWeight.value
                _isChangeWeightDialogShowing.value = true
            }
            
            ChangeWeightDialogEvent.DismissDialog -> {
                _isChangeWeightDialogShowing.value = false
            }
            
            is ChangeWeightDialogEvent.OnWeightChange -> {
                _selectedWeight.value = event.newWeight
            }
            
            ChangeWeightDialogEvent.SaveNewWeight -> {
                // save weight
                viewModelScope.launch {
                    preferences.saveWeight(selectedWeight.value)
                    _isChangeWeightDialogShowing.value = false
                }
            }
        }
    }
    
    /** Bed Time dialog events */
    fun onEvent(event: ChangeBedTimeDialogEvent) {
        when (event) {
            ChangeBedTimeDialogEvent.ShowDialog -> {
                _changeBedTimeDialogHour.value = currentBedTime.value.hour
                _changeBedTimeDialogMinute.value = currentBedTime.value.minute
                _isChangeBedTimeDialogShowing.value = true
            }
            
            ChangeBedTimeDialogEvent.DismissDialog -> {
                _isChangeBedTimeDialogShowing.value = false
            }
            
            is ChangeBedTimeDialogEvent.OnHourChange -> {
                _changeBedTimeDialogHour.value = event.hour
            }
            
            is ChangeBedTimeDialogEvent.OnMinuteChange -> {
                _changeBedTimeDialogMinute.value = event.minute
            }
            
            ChangeBedTimeDialogEvent.SaveNewBedTime -> {
                viewModelScope.launch {
                    getLocalTime(changeBedTimeDialogHour.value, changeBedTimeDialogMinute.value)
                        .onSuccess {
                            // Save bed time
                            preferences.saveBedTime(it)
                            _isChangeBedTimeDialogShowing.value = false
                        }
                        .onFailure {
                            // show error snackbar
                            _isChangeBedTimeDialogShowing.value = false
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(it.message ?: "Something went wrong")))
                        }
                }
            }
        }
    }
    
    /** Wakeup Time dialog events */
    fun onEvent(event: ChangeWakeupTimeDialogEvent) {
        when (event) {
            ChangeWakeupTimeDialogEvent.ShowDialog -> {
                _changeWakeupTimeDialogHour.value = currentWakeUpTime.value.hour
                _changeWakeupTimeDialogMinute.value = currentWakeUpTime.value.minute
                _isChangeWakeUpTimeDialogShowing.value = true
            }
            
            ChangeWakeupTimeDialogEvent.DismissDialog -> {
                _isChangeWakeUpTimeDialogShowing.value = false
            }
            
            is ChangeWakeupTimeDialogEvent.OnHourChange -> {
                _changeWakeupTimeDialogHour.value = event.hour
            }
            
            is ChangeWakeupTimeDialogEvent.OnMinuteChange -> {
                _changeWakeupTimeDialogMinute.value = event.minute
            }
            
            ChangeWakeupTimeDialogEvent.SaveNewWakeupTime -> {
                viewModelScope.launch {
                    getLocalTime(changeWakeupTimeDialogHour.value, changeWakeupTimeDialogMinute.value)
                        .onSuccess {
                            // Save wake time
                            preferences.saveWakeupTime(it)
                            _isChangeWakeUpTimeDialogShowing.value = false
                        }
                        .onFailure {
                            // show error snackbar
                            _isChangeWakeUpTimeDialogShowing.value = false
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(it.message ?: "Something went wrong")))
                        }
                }
            }
        }
    }
    
    
}