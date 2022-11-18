package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
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
    private val getADayDrinks: GetADayDrinks,
    private val getDrinkProgress: GetDrinkProgress,
    private val getTrackableDrinks: GetTrackableDrinks,
    private val getSelectedTrackableDrink: GetSelectedTrackableDrink,
    private val addDrink: AddDrink
) : ViewModel() {

    var state by mutableStateOf(WaterHomeState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //TODO calculate goal
    init {
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
                addDrink(state.selectedTrackableDrink)
                    .onSuccess {
                        // Do nothing for now
                    }
                    .onFailure {
                        showMessage(UiText.DynamicString(it.message ?: "Add some drinks"))
                    }
            }
            WaterHomeEvent.OnForgotDrinkClick -> {

            }
            is WaterHomeEvent.OnTrackableDrinkChange -> {
                state = state.copy(
                    selectedTrackableDrink = event.drink
                )
            }
            WaterHomeEvent.OnAddTrackableDrinkClick -> {

            }
            WaterHomeEvent.OnRemoveTrackableDrinkClick -> {
                waterRepository.removeTrackableDrink(state.selectedTrackableDrink)
            }
        }
    }

    private suspend fun showMessage(message: UiText) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(message)
        )
    }
}