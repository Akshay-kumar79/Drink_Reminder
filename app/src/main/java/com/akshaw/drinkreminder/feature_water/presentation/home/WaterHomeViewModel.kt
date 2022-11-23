package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.FilterOutDigits
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.akshaw.drinkreminder.feature_water.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WaterHomeViewModel @Inject constructor(
    private val waterRepository: WaterRepository,
    private val preferences: Preferences,
    private val getADayDrinks: GetADayDrinks,
    private val getDrinkProgress: GetDrinkProgress,
    private val getTrackableDrinks: GetTrackableDrinks,
    private val getSelectedTrackableDrink: GetSelectedTrackableDrink,
    private val filterOutDigits: FilterOutDigits,
    private val validateQuantity: ValidateQuantity,
    private val drinkNow: DrinkNow
) : ViewModel() {

    var state by mutableStateOf(WaterHomeState())
        private set

    var maxValue by mutableStateOf(7)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //TODO calculate goal
    init {
//        viewModelScope.launch {
//            for (amount in 1..7)
//                waterRepository.insertTrackableDrink(
//                    TrackableDrink(
//                        amount = amount * 100.0,
//                        unit = WaterUnit.ML
//                    )
//                )
//
//            for (amount in 1..7)
//                waterRepository.insertTrackableDrink(
//                    TrackableDrink(
//                        amount = amount * 10.0,
//                        unit = WaterUnit.FL_OZ
//                    )
//                )
//        }

        preferences.saveWaterUnit(WaterUnit.ML)
        state = state.copy(
            waterUnit = preferences.loadWaterUnit()
        )
        getADayDrinks(LocalDate.now()).onEach { drinks ->
            state = state.copy(
                progress = getDrinkProgress(drinks),
                drinks = drinks
            )
        }.launchIn(viewModelScope)

        getTrackableDrinks().onEach { trackableDrinks ->
            state = state.copy(
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
                drinkNow(state.selectedTrackableDrink)
                    .onSuccess {
                        // Do nothing for now
                    }
                    .onFailure {
                        showMessage(UiText.DynamicString(it.message ?: "Add some drinks"))
                    }
            }
            is WaterHomeEvent.OnTrackableDrinkChange -> {
                state = state.copy(
                    selectedTrackableDrink = event.drink
                )
            }
            is WaterHomeEvent.OnAddQuantityAmountChange -> {
                validateQuantity(event.amount)
                    .onSuccess {
                        state = state.copy(
                            addQuantityAmount = it
                        )
                    }
                    .onFailure {
                        // do nothing for now
                    }

            }
            WaterHomeEvent.OnAddTrackableDrinkClick -> {
                state = state.copy(
                    isAddTrackableDrinkDialogShowing = true
                )
            }
            is WaterHomeEvent.OnAddTrackableDrinkConfirm -> {
                waterRepository.insertTrackableDrink(
                    TrackableDrink(
                        amount = state.addQuantityAmount.toDoubleOrNull() ?: 0.0,
                        unit = state.waterUnit
                    )
                )
                state = state.copy(
                    addQuantityAmount = "",
                    isAddTrackableDrinkDialogShowing = false
                )
            }
            WaterHomeEvent.OnAddTrackableDrinkCancel -> {
                state = state.copy(
                    addQuantityAmount = "",
                    isAddTrackableDrinkDialogShowing = false
                )
            }

            WaterHomeEvent.OnRemoveTrackableDrinkClick -> {
                state = state.copy(
                    isRemoveTrackableDrinkDialogShowing = true
                )
            }
            WaterHomeEvent.OnRemoveTrackableDrinkConfirm -> {
                waterRepository.removeTrackableDrink(state.selectedTrackableDrink)
                state = state.copy(
                    isRemoveTrackableDrinkDialogShowing = false
                )
            }
            WaterHomeEvent.OnRemoveTrackableDrinkCancel -> {
                state = state.copy(
                    isRemoveTrackableDrinkDialogShowing = false
                )
            }

            WaterHomeEvent.OnAddForgotDrinkClick -> {
                state = state.copy(
                    isAddForgotDrinkDialogShowing = true
                )
            }
            is WaterHomeEvent.OnAddForgotDrinkConfirm -> {
                waterRepository.insertDrink(event.drink)
                state = state.copy(
                    isAddForgotDrinkDialogShowing = false
                )
            }
            WaterHomeEvent.OnAddForgotDrinkCancel -> {
                state = state.copy(
                    isAddForgotDrinkDialogShowing = false
                )
            }
        }
    }

    private suspend fun showMessage(message: UiText) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(message)
        )
    }
}