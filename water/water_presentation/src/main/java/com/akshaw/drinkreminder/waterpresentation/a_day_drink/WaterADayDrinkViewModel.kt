package com.akshaw.drinkreminder.waterpresentation.a_day_drink

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.corecompose.uievents.UiEvent
import com.akshaw.drinkreminder.corecompose.uievents.UiText
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.waterdomain.use_case.AddDrink
import com.akshaw.drinkreminder.waterdomain.use_case.AddForgottenDrink
import com.akshaw.drinkreminder.waterdomain.use_case.DeleteDrink
import com.akshaw.drinkreminder.waterdomain.use_case.FilterADayDrinks
import com.akshaw.drinkreminder.waterdomain.use_case.ValidateQuantity
import com.akshaw.drinkreminder.waterpresentation.common.events.DialogAddForgottenDrinkEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class WaterADayDrinkViewModel @Inject constructor(
    preferences: Preferences,
    savedStateHandle: SavedStateHandle,
    waterRepository: WaterRepository,
    private val filterADayDrinks: FilterADayDrinks,
    private val addDrink: AddDrink,
    private val validateQuantity: ValidateQuantity,
    private val deleteDrink: DeleteDrink,
    private val addForgottenDrink: AddForgottenDrink
) : ViewModel() {
    
    companion object {
        const val CURRENT_DAY_ARGUMENT = "currentDate"
    }
    
    /** Screen State */
    val currentDate: LocalDate = savedStateHandle.get<String>(CURRENT_DAY_ARGUMENT)?.let {
        LocalDate.parse(it)
    } ?: LocalDate.now()
    
    val todayDrinks = waterRepository.getAllDrink().map {
        filterADayDrinks(currentDate, it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    val waterUnit = preferences.getWaterUnit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WATER_UNIT)
    
    private var recentlyDeleteDrink: Drink? = null
    
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
    
    
    fun onEvent(event: WaterADayDrinkEvent) = viewModelScope.launch {
        when (event) {
            is WaterADayDrinkEvent.OnDrinkDeleteClick -> {
                deleteDrink(event.drink)
                recentlyDeleteDrink = event.drink
            }
            
            WaterADayDrinkEvent.RestoreDrink -> {
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
                addForgottenDrink(
                    addForgottenDrinkDialogHour.value,
                    addForgottenDrinkDialogMinute.value,
                    currentDate,
                    addForgottenDrinkDialogQuantity.value.toDoubleOrNull()
                ).onFailure {
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
                validateQuantity(event.amount, waterUnit.value)
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