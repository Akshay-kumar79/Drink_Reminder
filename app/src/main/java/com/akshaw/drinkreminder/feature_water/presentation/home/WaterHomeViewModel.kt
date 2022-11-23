package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.akshaw.drinkreminder.feature_water.domain.use_case.*
import com.akshaw.drinkreminder.feature_water.presentation.home.events.DialogAddForgottenDrinkEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.events.DialogAddTrackableDrinkEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.events.WaterHomeEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.state.DialogAddForgottenDrinkState
import com.akshaw.drinkreminder.feature_water.presentation.home.state.DialogAddTrackableDrinkState
import com.akshaw.drinkreminder.feature_water.presentation.home.state.WaterHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class WaterHomeViewModel @Inject constructor(
    private val waterRepository: WaterRepository,
    private val preferences: Preferences,
    private val getADayDrinks: GetADayDrinks,
    private val getDrinkProgress: GetDrinkProgress,
    private val getTrackableDrinks: GetTrackableDrinks,
    private val getSelectedTrackableDrink: GetSelectedTrackableDrink,
    private val getLocalTime: GetLocalTime,
    private val validateQuantity: ValidateQuantity,
    private val drinkNow: DrinkNow
) : ViewModel() {

    var screenState by mutableStateOf(WaterHomeState())
        private set

    var addForgottenDrinkDialogState by mutableStateOf(DialogAddForgottenDrinkState())
        private set

    var addTrackableDrinkDialogState by mutableStateOf(DialogAddTrackableDrinkState())
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // TODO calculate goal
    // TODO listen to preference changes
    init {
        preferences.saveWaterUnit(WaterUnit.ML)
        screenState = screenState.copy(
            waterUnit = preferences.loadWaterUnit()
        )
        getADayDrinks(LocalDate.now()).onEach { drinks ->
            screenState = screenState.copy(
                progress = getDrinkProgress(drinks),
                drinks = drinks
            )
        }.launchIn(viewModelScope)

        getTrackableDrinks().onEach { trackableDrinks ->
            screenState = screenState.copy(
                trackableDrinks = trackableDrinks,
                selectedTrackableDrink = getSelectedTrackableDrink(trackableDrinks)
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: WaterHomeEvent) = viewModelScope.launch {
        when (event) {
            WaterHomeEvent.OnReminderClick -> {

            }
            WaterHomeEvent.OnDrinkClick -> {
                drinkNow(screenState.selectedTrackableDrink)
                    .onSuccess {
                        // Do nothing for now
                    }
                    .onFailure {
                        showSnackbar(UiText.DynamicString(it.message ?: "Add some drinks"))
                    }
            }
            is WaterHomeEvent.OnTrackableDrinkChange -> {
                screenState = screenState.copy(
                    selectedTrackableDrink = event.drink
                )
            }

            WaterHomeEvent.OnRemoveTrackableDrinkClick -> {
                screenState = screenState.copy(
                    isRemoveTrackableDrinkDialogShowing = true
                )
            }
            WaterHomeEvent.OnRemoveTrackableDrinkConfirm -> {
                waterRepository.removeTrackableDrink(screenState.selectedTrackableDrink)
                screenState = screenState.copy(
                    isRemoveTrackableDrinkDialogShowing = false
                )
            }
            WaterHomeEvent.OnRemoveTrackableDrinkCancel -> {
                screenState = screenState.copy(
                    isRemoveTrackableDrinkDialogShowing = false
                )
            }


        }
    }

    fun onEvent(event: DialogAddForgottenDrinkEvent) = viewModelScope.launch {
        when (event) {
            DialogAddForgottenDrinkEvent.OnAddForgotDrinkClick -> {
                addForgottenDrinkDialogState = addForgottenDrinkDialogState.copy(
                    isDialogShowing = true
                )
            }
            DialogAddForgottenDrinkEvent.OnConfirmClick -> {
                getLocalTime(
                    addForgottenDrinkDialogState.hour,
                    addForgottenDrinkDialogState.minute
                )
                    .onSuccess { localTime ->
                        waterRepository.insertDrink(
                            Drink(
                                dateTime = LocalDateTime.of(
                                    LocalDate.now(),
                                    localTime
                                ),
                                waterIntake = addForgottenDrinkDialogState.quantity.toDoubleOrNull() ?: 0.0,
                                unit = screenState.waterUnit
                            )
                        )
                    }
                    .onFailure {
                        showSnackbar(UiText.DynamicString(it.message ?: "Something went wrong"))
                    }
                addForgottenDrinkDialogState = addForgottenDrinkDialogState.copy(
                    isDialogShowing = false,
                    quantity = "",
                    hour = LocalTime.now().hour,
                    minute = LocalTime.now().minute
                )
            }
            DialogAddForgottenDrinkEvent.OnCancelClick -> {
                addForgottenDrinkDialogState = addForgottenDrinkDialogState.copy(
                    isDialogShowing = false,
                    quantity = "",
                    hour = LocalTime.now().hour,
                    minute = LocalTime.now().minute
                )
            }
            is DialogAddForgottenDrinkEvent.OnHourChange -> {
                addForgottenDrinkDialogState = addForgottenDrinkDialogState.copy(
                    hour = event.hour
                )
            }
            is DialogAddForgottenDrinkEvent.OnMinuteChange -> {
                addForgottenDrinkDialogState = addForgottenDrinkDialogState.copy(
                    minute = event.minute
                )
            }
            is DialogAddForgottenDrinkEvent.OnQuantityAmountChange -> {
                validateQuantity(event.amount)
                    .onSuccess {
                        addForgottenDrinkDialogState = addForgottenDrinkDialogState.copy(
                            quantity = it
                        )
                    }
            }
        }
    }

    fun onEvent(event: DialogAddTrackableDrinkEvent) = viewModelScope.launch {
        when (event) {
            is DialogAddTrackableDrinkEvent.OnQuantityAmountChange -> {
                validateQuantity(event.amount)
                    .onSuccess {
                        addTrackableDrinkDialogState = addTrackableDrinkDialogState.copy(
                            quantity = it
                        )
                    }
                    .onFailure {
                        // do nothing for now
                    }

            }
            DialogAddTrackableDrinkEvent.OnAddTrackableDrinkClick -> {
                addTrackableDrinkDialogState = addTrackableDrinkDialogState.copy(
                    isDialogShowing = true
                )
            }
            is DialogAddTrackableDrinkEvent.OnConfirmClick -> {
                waterRepository.insertTrackableDrink(
                    TrackableDrink(
                        amount = addTrackableDrinkDialogState.quantity.toDoubleOrNull() ?: 0.0,
                        unit = screenState.waterUnit
                    )
                )
                addTrackableDrinkDialogState = addTrackableDrinkDialogState.copy(
                    quantity = "",
                    isDialogShowing = false
                )
            }
            DialogAddTrackableDrinkEvent.OnCancelClick -> {
                addTrackableDrinkDialogState = addTrackableDrinkDialogState.copy(
                    quantity = "",
                    isDialogShowing = false
                )
            }
        }
    }

    private suspend fun showSnackbar(message: UiText) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(message)
        )
    }
}