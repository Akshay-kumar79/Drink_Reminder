package com.akshaw.drinkreminder.waterpresentation.a_day_drink

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.waterdomain.repository.WaterRepository
import com.akshaw.drinkreminder.waterdomain.use_case.AddDrink
import com.akshaw.drinkreminder.waterdomain.use_case.DeleteDrink
import com.akshaw.drinkreminder.waterdomain.use_case.FilterADayDrinks
import com.akshaw.drinkreminder.waterdomain.use_case.ValidateQuantity
import com.akshaw.drinkreminder.waterpresentation.common.events.DialogAddForgottenDrinkEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class WaterADayDrinkViewModel @Inject constructor(
    private val preferences: Preferences,
    private val savedStateHandle: SavedStateHandle,
    private val waterRepository: WaterRepository,
    private val filterADayDrinks: FilterADayDrinks,
    private val getLocalTime: GetLocalTime,
    private val addDrink: AddDrink,
    private val validateQuantity: ValidateQuantity,
    private val deleteDrink: DeleteDrink
) : ViewModel() {
    
    companion object {
        const val CURRENT_DAY_ARGUMENT = "currentDate"
    }
    
    /** Screen State */
    val currentDate = savedStateHandle.get<String>(CURRENT_DAY_ARGUMENT)?.let {
        LocalDate.parse(it)
    } ?: LocalDate.now()
    
    val todayDrinks = waterRepository.getAllDrink().map {
        filterADayDrinks(currentDate, it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    private val _waterUnit = MutableStateFlow(preferences.loadWaterUnit())
    val waterUnit = _waterUnit.asStateFlow()
    
    var recentlyDeleteDrink: com.akshaw.drinkreminder.waterdomain.model.Drink? = null
    
    /** Add Forgotten Drink Dialog States */
    private val _isAddForgottenDrinkDialogShowing = MutableStateFlow(false)
    val isAddForgottenDrinkDialogShowing = _isAddForgottenDrinkDialogShowing.asStateFlow()
    
    private val _addForgottenDrinkDialogQuantity = MutableStateFlow("")
    val addForgottenDrinkDialogQuantity = _addForgottenDrinkDialogQuantity.asStateFlow()
    
    private val _addForgottenDrinkDialogHour = MutableStateFlow(LocalTime.now().hour)
    val addForgottenDrinkDialogHour = _addForgottenDrinkDialogHour.asStateFlow()
    
    private val _addForgottenDrinkDialogMinute = MutableStateFlow(LocalTime.now().minute)
    val addForgottenDrinkDialogMinute = _addForgottenDrinkDialogMinute.asStateFlow()
    
    
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    
    
    fun onEvent(event: com.akshaw.drinkreminder.waterpresentation.a_day_drink.WaterADayDrinkEvent) = viewModelScope.launch {
        when (event) {
            is com.akshaw.drinkreminder.waterpresentation.a_day_drink.WaterADayDrinkEvent.OnDrinkDeleteClick -> {
                deleteDrink(event.drink)
                recentlyDeleteDrink = event.drink
            }
            com.akshaw.drinkreminder.waterpresentation.a_day_drink.WaterADayDrinkEvent.RestoreDrink -> {
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
                            com.akshaw.drinkreminder.waterdomain.model.Drink(
                                dateTime = LocalDateTime.of(
                                    currentDate,
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
    
    private suspend fun showSnackbar(message: UiText) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(message)
        )
    }
    
}