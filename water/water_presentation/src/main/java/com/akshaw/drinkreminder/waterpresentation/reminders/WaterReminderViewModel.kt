package com.akshaw.drinkreminder.waterpresentation.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.util.ReminderType
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.UiText
import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.CancelDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.DeleteDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.GetAllDrinkReminders
import com.akshaw.drinkreminder.waterdomain.use_case.ScheduleDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.UpsertDrinkReminder
import com.akshaw.drinkreminder.waterpresentation.reminders.events.RemindersEvent
import com.akshaw.drinkreminder.waterpresentation.reminders.events.TSReminderMode
import com.akshaw.drinkreminder.waterpresentation.reminders.events.UpsertReminderDialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class WaterReminderViewModel @Inject constructor(
    getAllDrinkReminders: GetAllDrinkReminders,
    private val upsertDrinkReminder: UpsertDrinkReminder,
    private val deleteDrinkReminder: DeleteDrinkReminder,
    private val scheduleDrinkReminder: ScheduleDrinkReminder,
    private val cancelDrinkReminder: CancelDrinkReminder
) : ViewModel() {
    
    /** Screen states */
    val allDrinkReminders = getAllDrinkReminders().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
    
    val allReminderTypes = listOf(ReminderType.TSReminder, ReminderType.AIReminder)
    
    private val _isSelectReminderTypeExpanded = MutableStateFlow(false)
    val isSelectReminderTypeExpanded = _isSelectReminderTypeExpanded.asStateFlow()
    
    private val _selectedReminderType = MutableStateFlow(allReminderTypes[0])
    val selectedReminderType = _selectedReminderType.asStateFlow()
    
    
    /** Upsert Reminder dialog states */
    private val _isSetReminderDialogShowing = MutableStateFlow(false)
    val isSetReminderDialogShowing = _isSetReminderDialogShowing.asStateFlow()
    
    // map with key as DayOfWeek.value and value as true of false (if day is selected or not).
    private val _selectedDays = MutableStateFlow(DayOfWeek.values().associateBy({ it.value }, { true }))
    val selectedDays = _selectedDays.asStateFlow()
    
    private val _setReminderDialogHour = MutableStateFlow(LocalTime.now().hour)
    val setReminderDialogHour = _setReminderDialogHour.asStateFlow()
    
    private val _setReminderDialogMinute = MutableStateFlow(LocalTime.now().minute)
    val setReminderDialogMinute = _setReminderDialogMinute.asStateFlow()
    
    // To keep track of whether UpsertReminderDialog is opened to AddNewReminder or UpdateReminder(with DrinkReminder to be updated)
    // init when upsert dialog opened
    // clear when upsert dialog dismiss
    private var currentTSReminderMode: TSReminderMode? = null
    
    
    /** One time events */
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    
    
    init {
        // clear the currentTsReminderMode when set reminder dialog dismiss. To be sure to not add or update reminder by some glitch
        viewModelScope.launch {
            isSetReminderDialogShowing
                .collect { isShowing ->
                    if (!isShowing) {
                        currentTSReminderMode = null
                    }
                }
        }
    }
    
    /** Screen Events */
    fun onEvent(event: RemindersEvent) {
        when (event) {
            is RemindersEvent.OnReminderSwitched -> {
                // reschedule and update reminder in database
                viewModelScope.launch(Dispatchers.IO) {
                    upsertDrinkReminder(event.drinkReminder.copy(isReminderOn = event.isReminderOn))
                    if (event.isReminderOn) {
                        scheduleDrinkReminder(event.drinkReminder)
                    } else {
                        cancelDrinkReminder(event.drinkReminder)
                    }
                }
            }
            
            is RemindersEvent.OnSelectedReminderTypeChange -> {
                // _selectedReminderType.value = event.reminderType
                if (event.reminderType != ReminderType.TSReminder)
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString("Feature Coming soon")))
                    }
            }
            
            is RemindersEvent.OnReminderTypeDropdownExpandedChange -> {
                _isSelectReminderTypeExpanded.value = event.isShowing
            }
            
            is RemindersEvent.OnDeleteReminder -> {
                viewModelScope.launch(Dispatchers.IO){
                    cancelDrinkReminder(event.drinkReminder)
                    deleteDrinkReminder(event.drinkReminder)
                }
            }
        }
    }
    
    
    /** Upsert Reminder Dialog Events */
    fun onEvent(event: UpsertReminderDialogEvent) {
        when (event) {
            is UpsertReminderDialogEvent.ShowDialog -> {
                // reset all selected days to true and show dialog
                currentTSReminderMode = event.reminderMode
                when (event.reminderMode) {
                    TSReminderMode.AddNewReminder -> {
                        _selectedDays.value = DayOfWeek.values().associateBy({ it.value }, { true })
                        _setReminderDialogHour.value = LocalTime.now().hour
                        _setReminderDialogMinute.value = LocalTime.now().minute
                    }
                    
                    is TSReminderMode.UpdateReminder -> {
                        _selectedDays.value = DayOfWeek.values().associateBy({ it.value }, { event.reminderMode.drinkReminder.activeDays.contains(it) })
                        _setReminderDialogHour.value = event.reminderMode.drinkReminder.time.hour
                        _setReminderDialogMinute.value = event.reminderMode.drinkReminder.time.minute
                    }
                }
                _isSetReminderDialogShowing.value = true
            }
            
            UpsertReminderDialogEvent.DismissDialog -> {
                _isSetReminderDialogShowing.value = false
            }
            
            is UpsertReminderDialogEvent.OnChangeDayOfWeeks -> {
                // revert day of week selection in selectedDays
                _selectedDays.value = selectedDays.value.toMutableMap().apply {
                    set(event.dayOfWeek.value, !selectedDays.value.getOrDefault(event.dayOfWeek.value, false))
                }
            }
            
            UpsertReminderDialogEvent.OnDoneClick -> {
                // save reminder and dismiss dialog
                viewModelScope.launch(Dispatchers.IO) {
                    currentTSReminderMode?.let {
                        when (it) {
                            TSReminderMode.AddNewReminder -> {
                                val drinkReminder = DrinkReminder(
                                    time = LocalTime.now().withHour(setReminderDialogHour.value).withMinute(setReminderDialogMinute.value),
                                    isReminderOn = true,
                                    activeDays = DayOfWeek.values().filter {
                                        selectedDays.value[it.value] == true
                                    }
                                )
                                
                                val id = upsertDrinkReminder(drinkReminder)
                                scheduleDrinkReminder(drinkReminder.copy(id = id))
                            }
                            
                            is TSReminderMode.UpdateReminder -> {
                                val drinkReminder = DrinkReminder(
                                    id = it.drinkReminder.id,
                                    time = LocalTime.now().withHour(setReminderDialogHour.value).withMinute(setReminderDialogMinute.value),
                                    isReminderOn = it.drinkReminder.isReminderOn,
                                    activeDays = DayOfWeek.values().filter {
                                        selectedDays.value[it.value] == true
                                    }
                                )
                                
                                upsertDrinkReminder(drinkReminder)
                                if (it.drinkReminder.isReminderOn)
                                    scheduleDrinkReminder(drinkReminder)
                            }
                        }
                    } ?: kotlin.run {
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString("Something went wrong")))
                    }
                    _isSetReminderDialogShowing.value = false
                }
            }
            
            is UpsertReminderDialogEvent.OnHourChange -> {
                _setReminderDialogHour.value = event.hour
            }
            
            is UpsertReminderDialogEvent.OnMinuteChange -> {
                _setReminderDialogMinute.value = event.minute
            }
        }
    }
    
}