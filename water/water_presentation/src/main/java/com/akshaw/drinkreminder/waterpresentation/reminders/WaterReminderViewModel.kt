package com.akshaw.drinkreminder.waterpresentation.reminders

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.preferences.elements.ReminderType
import com.akshaw.drinkreminder.core.util.NoExactAlarmPermissionException
import com.akshaw.drinkreminder.core.util.NoNotificationPermissionException
import com.akshaw.drinkreminder.corecompose.uievents.UiEvent
import com.akshaw.drinkreminder.corecompose.uievents.UiText
import com.akshaw.drinkreminder.waterdomain.use_case.CancelDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.DeleteDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.GetAllDrinkReminders
import com.akshaw.drinkreminder.corecompose.events.ExactAlarmPermissionDialogEvent
import com.akshaw.drinkreminder.corecompose.events.NotificationPermissionDialogEvent
import com.akshaw.drinkreminder.waterdomain.use_case.AddAndScheduleDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.SwitchDrinkReminder
import com.akshaw.drinkreminder.waterdomain.use_case.UpdateAndScheduleDrinkReminder
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
    private val deleteDrinkReminder: DeleteDrinkReminder,
    private val cancelDrinkReminder: CancelDrinkReminder,
    private val switchDrinkReminder: SwitchDrinkReminder,
    private val addAndScheduleDrinkReminder: AddAndScheduleDrinkReminder,
    private val updateAndScheduleDrinkReminder: UpdateAndScheduleDrinkReminder
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
    
    
    // mutable state of dialog to be shown when notification permission is not granted, for greater than android 13
    private val _isReRequestNotificationPermDialogVisible = MutableStateFlow(false)
    val isReRequestNotificationPermDialogVisible = _isReRequestNotificationPermDialogVisible.asStateFlow()
    
    // mutable state of dialog to be shown when exact alarm permission is not granted, for android 12
    private val _isReRequestExactAlarmPermDialogVisible = MutableStateFlow(false)
    val isReRequestExactAlarmPermDialogVisible = _isReRequestExactAlarmPermDialogVisible.asStateFlow()
    
    
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
                    switchDrinkReminder(event.drinkReminder, event.isReminderOn)
                        .onFailure {
                            when (it) {
                                is NoNotificationPermissionException -> {
                                    _isReRequestNotificationPermDialogVisible.value = true
                                }
                                
                                is NoExactAlarmPermissionException -> {
                                    _isReRequestExactAlarmPermDialogVisible.value = true
                                }
                            }
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
                viewModelScope.launch(Dispatchers.IO) {
                    cancelDrinkReminder(event.drinkReminder)
                    deleteDrinkReminder(event.drinkReminder)
                }
            }
            
            is RemindersEvent.OnPermissionResult -> {
                if (!event.isGranted) {
                    when (event.permission) {
                        Manifest.permission.POST_NOTIFICATIONS -> {
                            _isReRequestNotificationPermDialogVisible.value = true
                        }
                    }
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
                // revert day of week selection in selectedDays (if day of week selected then unselect or if it is unselected and select it)
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
                                addAndScheduleDrinkReminder(setReminderDialogHour.value, setReminderDialogMinute.value, selectedDays.value)
                                    .onFailure { throwable ->
                                        throwable.message?.let { errorMessage ->
                                            showSnackbar(UiText.DynamicString(errorMessage))
                                        } ?: showSnackbar(UiText.StringResource(R.string.error_something_went_wrong))
                                    }
                            }
                            
                            is TSReminderMode.UpdateReminder -> {
                                updateAndScheduleDrinkReminder(it.drinkReminder, setReminderDialogHour.value, setReminderDialogMinute.value, selectedDays.value)
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
    
    fun onEvent(event: NotificationPermissionDialogEvent) {
        when (event) {
            NotificationPermissionDialogEvent.ShowDialog -> {
                _isReRequestNotificationPermDialogVisible.value = true
            }
            
            NotificationPermissionDialogEvent.DismissDialog -> {
                _isReRequestNotificationPermDialogVisible.value = false
            }
        }
    }
    
    fun onEvent(event: ExactAlarmPermissionDialogEvent) {
        when (event) {
            ExactAlarmPermissionDialogEvent.ShowDialog -> {
                _isReRequestExactAlarmPermDialogVisible.value = true
            }
            
            ExactAlarmPermissionDialogEvent.DismissDialog -> {
                _isReRequestExactAlarmPermDialogVisible.value = false
            }
        }
    }
    
    private suspend fun showSnackbar(message: UiText) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(message)
        )
    }
    
}