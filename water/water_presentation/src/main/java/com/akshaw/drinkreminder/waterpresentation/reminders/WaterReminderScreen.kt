package com.akshaw.drinkreminder.waterpresentation.reminders

import android.Manifest
import android.annotation.SuppressLint
import android.view.SoundEffectConstants
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.preferences.elements.ReminderType
import com.akshaw.drinkreminder.core.domain.preferences.elements.getDescription
import com.akshaw.drinkreminder.core.domain.preferences.elements.getText
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.corecompose.composables.ExactAlarmPermissionTextProvider
import com.akshaw.drinkreminder.corecompose.composables.NotificationPermissionTextProvider
import com.akshaw.drinkreminder.corecompose.composables.PermissionDialog
import com.akshaw.drinkreminder.corecompose.events.ExactAlarmPermissionDialogEvent
import com.akshaw.drinkreminder.corecompose.events.NotificationPermissionDialogEvent
import com.akshaw.drinkreminder.waterpresentation.reminders.components.AIReminderSection
import com.akshaw.drinkreminder.waterpresentation.reminders.components.TSReminderSection
import com.akshaw.drinkreminder.waterpresentation.reminders.dialogs.UpsertReminderDialog
import com.akshaw.drinkreminder.waterpresentation.reminders.events.RemindersEvent
import com.akshaw.drinkreminder.waterpresentation.reminders.events.TSReminderMode
import com.akshaw.drinkreminder.waterpresentation.reminders.events.UpsertReminderDialogEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("InlinedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterReminderScreen(
    viewModel: WaterReminderViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onBackClicked: () -> Unit,
    shouldShowRequestPermissionRationale: (permission: String) -> Boolean,
    openAppSettings: () -> Unit,
    openAppSetExactAlarmPermissionSettings: () -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current
    
    val isReRequestNotificationPermDialogVisible by viewModel.isReRequestNotificationPermDialogVisible.collectAsState()
    val isReRequestExactAlarmPermDialogVisible by viewModel.isReRequestExactAlarmPermDialogVisible.collectAsState()
    
    val notificationPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.onEvent(RemindersEvent.OnPermissionResult(Manifest.permission.POST_NOTIFICATIONS, it))
    }
    
    
    val isSelectReminderTypeExpanded by viewModel.isSelectReminderTypeExpanded.collectAsState()
    val selectedReminderType by viewModel.selectedReminderType.collectAsState()
    val allDrinkReminders by viewModel.allDrinkReminders.collectAsState()
    
    // Set Reminder Dialog States
    val isBottomSheetVisible by viewModel.isSetReminderDialogShowing.collectAsState()
    val selectedDays by viewModel.selectedDays.collectAsState()
    val setReminderDialogHour by viewModel.setReminderDialogHour.collectAsState()
    val setReminderDialogMinute by viewModel.setReminderDialogMinute.collectAsState()
    
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
                
                else -> Unit
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            
            // Toolbar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 4.dp),
                        onClick = {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onBackClicked()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.left_arrow_icon),
                            contentDescription = null
                        )
                    }
                    
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Reminders",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.ubuntu_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            
            // Reminder type selector
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                expanded = isSelectReminderTypeExpanded,
                onExpandedChange = {
                    viewModel.onEvent(RemindersEvent.OnReminderTypeDropdownExpandedChange(!isSelectReminderTypeExpanded))
                },
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .focusProperties {
                            this.canFocus = false
                        },
                    readOnly = true,
                    value = selectedReminderType.getText(),
                    onValueChange = {},
                    label = { Text("Reminder Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSelectReminderTypeExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp
                    )
                )
                
                ExposedDropdownMenu(
                    modifier = Modifier
                        .exposedDropdownSize(true),
                    expanded = isSelectReminderTypeExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(RemindersEvent.OnReminderTypeDropdownExpandedChange(false))
                    },
                ) {
                    viewModel.allReminderTypes.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = selectionOption.getText(),
                                    fontSize = 14.sp
                                )
                            },
                            onClick = {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                viewModel.onEvent(RemindersEvent.OnSelectedReminderTypeChange(selectionOption))
                                viewModel.onEvent(RemindersEvent.OnReminderTypeDropdownExpandedChange(false))
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            // Reminder type description
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 72.dp),
                text = selectedReminderType.getDescription(),
                fontSize = 10.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_regular,
                        FontWeight.Normal
                    )
                ),
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(14f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            
            // Reminder type sections
            when (selectedReminderType) {
                ReminderType.AIReminder -> AIReminderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .clickable {
        
                        }
                )
                
                ReminderType.TSReminder -> TSReminderSection(
                    allDrinkReminders = allDrinkReminders,
                    onAddNewReminderClick = {
                        viewModel.onEvent(UpsertReminderDialogEvent.ShowDialog(TSReminderMode.AddNewReminder))
                    },
                    onReminderClick = {
                        viewModel.onEvent(UpsertReminderDialogEvent.ShowDialog(TSReminderMode.UpdateReminder(it)))
                    },
                    onReminderSwitchChange = { drinkReminder, isReminderOn ->
                        viewModel.onEvent(RemindersEvent.OnReminderSwitched(drinkReminder, isReminderOn))
                    },
                    onDeleteReminder = {
                        viewModel.onEvent(RemindersEvent.OnDeleteReminder(it))
                    }
                )
            }
        }
        
    }
    
    if (isBottomSheetVisible)
        UpsertReminderDialog(
            selectedDays = selectedDays,
            onCancel = { viewModel.onEvent(UpsertReminderDialogEvent.DismissDialog) },
            onButtonClick = { viewModel.onEvent(UpsertReminderDialogEvent.OnDoneClick) },
            hour = setReminderDialogHour,
            minute = setReminderDialogMinute,
            onHourChange = { viewModel.onEvent(UpsertReminderDialogEvent.OnHourChange(it)) },
            onMinuteChange = { viewModel.onEvent(UpsertReminderDialogEvent.OnMinuteChange(it)) },
            onDaySelectionChange = { viewModel.onEvent(UpsertReminderDialogEvent.OnChangeDayOfWeeks(it)) }
        )
    
    if (isReRequestNotificationPermDialogVisible) {
        PermissionDialog(
            permissionTextProvider = NotificationPermissionTextProvider(),
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS),
            onDismiss = { viewModel.onEvent(NotificationPermissionDialogEvent.DismissDialog) },
            onOkClick = {
                viewModel.onEvent(NotificationPermissionDialogEvent.DismissDialog)
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            },
            onGoToAppSettingsClick = {
                viewModel.onEvent(NotificationPermissionDialogEvent.DismissDialog)
                openAppSettings()
            }
        )
    }
    
    if (isReRequestExactAlarmPermDialogVisible) {
        PermissionDialog(
            permissionTextProvider = ExactAlarmPermissionTextProvider(),
            isPermanentlyDeclined = false,
            onDismiss = { viewModel.onEvent(ExactAlarmPermissionDialogEvent.DismissDialog) },
            onOkClick = {
                viewModel.onEvent(ExactAlarmPermissionDialogEvent.DismissDialog)
                openAppSetExactAlarmPermissionSettings()
            },
            onGoToAppSettingsClick = {
                viewModel.onEvent(ExactAlarmPermissionDialogEvent.DismissDialog)
                openAppSetExactAlarmPermissionSettings()
            }
        )
    }
}