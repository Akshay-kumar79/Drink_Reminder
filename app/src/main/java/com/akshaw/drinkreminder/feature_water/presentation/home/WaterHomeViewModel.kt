package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.use_case.*
import com.akshaw.drinkreminder.feature_water.presentation.home.events.DialogAddForgottenDrinkEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.events.DialogAddTrackableDrinkEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.events.DialogRemoveTrackableDrinkEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.events.WaterHomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class WaterHomeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterADayDrinks: FilterADayDrinks,
    private val getDrinkProgress: GetDrinkProgress,
    private val getAllTrackableDrinks: GetAllTrackableDrinks,
    private val filterTrackableDrinksByUnit: FilterTrackableDrinksByUnit,
    private val getSelectedTrackableDrink: GetSelectedTrackableDrink,
    private val getLocalTime: GetLocalTime,
    private val validateQuantity: ValidateQuantity,
    private val drinkNow: DrinkNow,
    private val addTrackableDrink: AddTrackableDrink,
    private val addDrink: AddDrink,
    private val deleteTrackableDrink: DeleteTrackableDrink,
    private val deleteDrink: DeleteDrink,
    private val getAllDrinks: GetAllDrinks
) : ViewModel() {
    
    
    /** Screen States */
    val goal = MutableStateFlow(2343.0).asStateFlow()
    
    val todayDrinks = getAllDrinks().map {
        filterADayDrinks(LocalDate.now(), it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    val progress = todayDrinks.map {
        getDrinkProgress(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    private val _waterUnit = MutableStateFlow(preferences.loadWaterUnit())
    val waterUnit = _waterUnit.asStateFlow()
    
    val trackableDrinks = combine(getAllTrackableDrinks(), waterUnit) { allTrackableDrinks, waterUnit ->
        filterTrackableDrinksByUnit(waterUnit, allTrackableDrinks)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    private val _selectedTrackableDrink = MutableStateFlow(TrackableDrink(-1, 0.0, WaterUnit.ML))
    val selectedTrackableDrink = _selectedTrackableDrink.asStateFlow()
    
    var recentlyDeleteDrink: Drink? = null
    
    
    /** Add Forgotten Drink Dialog States */
    private val _isAddForgottenDrinkDialogShowing = MutableStateFlow(false)
    val isAddForgottenDrinkDialogShowing = _isAddForgottenDrinkDialogShowing.asStateFlow()
    
    private val _addForgottenDrinkDialogQuantity = MutableStateFlow("")
    val addForgottenDrinkDialogQuantity = _addForgottenDrinkDialogQuantity.asStateFlow()
    
    private val _addForgottenDrinkDialogHour = MutableStateFlow(LocalTime.now().hour)
    val addForgottenDrinkDialogHour = _addForgottenDrinkDialogHour.asStateFlow()
    
    private val _addForgottenDrinkDialogMinute = MutableStateFlow(LocalTime.now().minute)
    val addForgottenDrinkDialogMinute = _addForgottenDrinkDialogMinute.asStateFlow()
    
    
    /** Add Trackable Drink Dialog States */
    private val _isAddTrackableDrinkDialogShowing = MutableStateFlow(false)
    val isAddTrackableDrinkDialogShowing = _isAddTrackableDrinkDialogShowing.asStateFlow()
    
    private val _addTrackableDrinkDialogQuantity = MutableStateFlow("")
    val addTrackableDrinkDialogQuantity = _addTrackableDrinkDialogQuantity.asStateFlow()
    
    
    /** Remove Trackable Drink Dialog States */
    private val _isRemoveTrackableDrinkDialogShowing = MutableStateFlow(false)
    val isRemoveTrackableDrinkDialogShowing = _isRemoveTrackableDrinkDialogShowing.asStateFlow()
    
    
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    
    // TODO calculate goal
    // TODO listen to preference changes
    init {
        trackableDrinks.onEach {
            _selectedTrackableDrink.value = getSelectedTrackableDrink(it)
        }.launchIn(viewModelScope)
    }
    
    fun onEvent(event: WaterHomeEvent) = viewModelScope.launch {
        when (event) {
            WaterHomeEvent.OnReminderClick -> {
            
            }
            WaterHomeEvent.OnDrinkClick -> {
                drinkNow(selectedTrackableDrink.value)
                    .onSuccess {
                        // Do nothing for now
                    }
                    .onFailure {
                        showSnackbar(UiText.DynamicString(it.message ?: "Something went wrong"))
                    }
            }
            is WaterHomeEvent.OnTrackableDrinkChange -> {
                _selectedTrackableDrink.value = event.drink
            }
            is WaterHomeEvent.OnDrinkDeleteClick -> {
                deleteDrink(event.drink)
                recentlyDeleteDrink = event.drink
            }
            WaterHomeEvent.RestoreDrink -> {
                addDrink(recentlyDeleteDrink ?: return@launch)
                    .onFailure {
                        showSnackbar(UiText.DynamicString(it.message ?: "Something went wrong"))
                    }
                recentlyDeleteDrink = null
            }
        }
    }
    
    fun onEvent(event: DialogAddForgottenDrinkEvent) = viewModelScope.launch {
        when (event) {
            DialogAddForgottenDrinkEvent.OnAddForgotDrinkClick -> {
                _addForgottenDrinkDialogQuantity.value = ""
                _addForgottenDrinkDialogHour.value = LocalTime.now().hour
                _addForgottenDrinkDialogMinute.value = LocalTime.now().minute
                _isAddForgottenDrinkDialogShowing.value = true
            }
            DialogAddForgottenDrinkEvent.OnConfirmClick -> {
                getLocalTime(
                    addForgottenDrinkDialogHour.value,
                    addForgottenDrinkDialogMinute.value
                )
                    .onSuccess { localTime ->
                        addDrink(
                            Drink(
                                dateTime = LocalDateTime.of(
                                    LocalDate.now(),
                                    localTime
                                ),
                                waterIntake = addForgottenDrinkDialogQuantity.value.toDoubleOrNull() ?: 0.0,
                                unit = waterUnit.value
                            )
                        ).onFailure {
                            showSnackbar(UiText.DynamicString(it.message ?: "Something went wrong"))
                        }
                    }
                    .onFailure {
                        showSnackbar(UiText.DynamicString(it.message ?: "Something went wrong"))
                    }
                _isAddForgottenDrinkDialogShowing.value = false
            }
            DialogAddForgottenDrinkEvent.OnDismiss -> {
                _isAddForgottenDrinkDialogShowing.value = false
            }
            is DialogAddForgottenDrinkEvent.OnHourChange -> {
                _addForgottenDrinkDialogHour.value = event.hour
            }
            is DialogAddForgottenDrinkEvent.OnMinuteChange -> {
                _addForgottenDrinkDialogMinute.value = event.minute
            }
            is DialogAddForgottenDrinkEvent.OnQuantityAmountChange -> {
                validateQuantity(event.amount)
                    .onSuccess {
                        _addForgottenDrinkDialogQuantity.value = it
                    }
            }
        }
    }
    
    fun onEvent(event: DialogAddTrackableDrinkEvent) = viewModelScope.launch {
        when (event) {
            is DialogAddTrackableDrinkEvent.OnQuantityAmountChange -> {
                validateQuantity(event.amount)
                    .onSuccess {
                        _addTrackableDrinkDialogQuantity.value = it
                    }
                    .onFailure {
                        // do nothing for now
                    }
                
            }
            DialogAddTrackableDrinkEvent.OnAddTrackableDrinkClick -> {
                _addTrackableDrinkDialogQuantity.value = ""
                _isAddTrackableDrinkDialogShowing.value = true
            }
            is DialogAddTrackableDrinkEvent.OnConfirmClick -> {
                addTrackableDrink(
                    TrackableDrink(
                        amount = addTrackableDrinkDialogQuantity.value.toDoubleOrNull() ?: 0.0,
                        unit = waterUnit.value
                    )
                ).onFailure {
                    showSnackbar(UiText.DynamicString(it.message ?: "Something went wrong"))
                }
                _isAddTrackableDrinkDialogShowing.value = false
            }
            DialogAddTrackableDrinkEvent.OnDismiss -> {
                _isAddTrackableDrinkDialogShowing.value = false
            }
        }
    }
    
    fun onEvent(event: DialogRemoveTrackableDrinkEvent) = viewModelScope.launch {
        when (event) {
            DialogRemoveTrackableDrinkEvent.OnRemoveTrackableDrinkClick -> {
                _isRemoveTrackableDrinkDialogShowing.value = true
            }
            DialogRemoveTrackableDrinkEvent.OnConfirmClick -> {
                deleteTrackableDrink(selectedTrackableDrink.value)
                _isRemoveTrackableDrinkDialogShowing.value = false
            }
            DialogRemoveTrackableDrinkEvent.OnDismiss -> {
                _isRemoveTrackableDrinkDialogShowing.value = false
            }
        }
    }
    
    private suspend fun showSnackbar(message: UiText) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(message)
        )
    }
    
}