package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.feature_water.domain.use_case.GetADayDrinks
import com.akshaw.drinkreminder.feature_water.domain.use_case.GetDrinkProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WaterHomeViewModel @Inject constructor(
    private val getADayDrinks: GetADayDrinks,
    private val getDrinkProgress: GetDrinkProgress
) : ViewModel() {

    var state by mutableStateOf(WaterHomeState())
        private set

    init {

        getADayDrinks(LocalDate.now()).onEach { drinks ->
            state = state.copy(
                progress = getDrinkProgress(drinks),
                drinks = drinks
            )
        }.launchIn(viewModelScope)

    }

    fun onEvent(event: WaterHomeEvent) {
        when (event) {
            WaterHomeEvent.OnAddTrackableDrinkClick -> {

            }
            WaterHomeEvent.OnDrinkClick -> {

            }
            WaterHomeEvent.OnForgotDrinkClick -> {

            }
            WaterHomeEvent.OnReminderClick -> {

            }
            WaterHomeEvent.OnRemoveTrackableDrinkClick -> {

            }
            is WaterHomeEvent.OnTrackableDrinkChange -> {

            }
        }
    }


}