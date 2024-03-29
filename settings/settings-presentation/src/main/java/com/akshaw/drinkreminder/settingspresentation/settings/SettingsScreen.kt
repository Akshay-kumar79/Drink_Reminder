package com.akshaw.drinkreminder.settingspresentation.settings

import android.content.Intent
import android.net.Uri
import android.view.SoundEffectConstants
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.corecompose.uievents.UiEvent
import com.akshaw.drinkreminder.settingspresentation.settings.components.*
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.ChangeAgeDialog
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.ChangeGenderDialog
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.ChangeTimeDialog
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.ChangeUnitDialog
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.ChangeWeightDialog
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.DailyIntakeGoalDialog
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeAgeDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeBedTimeDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeGenderDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeUnitDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeWakeupTimeDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.ChangeWeightDialogEvent
import com.akshaw.drinkreminder.settingspresentation.settings.events.DailyIntakeGoalDialogEvent
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.floor

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onRemindersClick: () -> Unit,
    onFaqClick: () -> Unit,
    onBugReportClick: () -> Unit
) {
    
    val context = LocalContext.current
    val view = LocalView.current
    
    
    val isUnitDialogShowing by viewModel.isChangeUnitDialogShowing.collectAsState()
    val currentWaterUnit by viewModel.currentWaterUnit.collectAsState()
    val currentWeightUnit by viewModel.currentWeightUnit.collectAsState()
    val selectedWaterUnit by viewModel.selectedWaterUnit.collectAsState()
    val selectedWeightUnit by viewModel.selectedWeightUnit.collectAsState()
    
    val isDailyIntakeGoalDialogShowing by viewModel.isChangeDailyGoalDialogShowing.collectAsState()
    val currentDailyIntakeGoal by viewModel.dailyIntakeGoal.collectAsState()
    val selectedDailyIntakeGoal by viewModel.selectedDailyIntakeGoal.collectAsState()
    
    val isGenderDialogShowing by viewModel.isChangeGenderDialogShowing.collectAsState()
    val currentGender by viewModel.currentGender.collectAsState()
    val selectedGender by viewModel.currentGender.collectAsState()
    
    val isAgeDialogShowing by viewModel.isChangeAgeDialogShowing.collectAsState()
    val currentAge by viewModel.currentAge.collectAsState()
    val selectedAge by viewModel.selectedAge.collectAsState()
    
    val isWeightDialogShowing by viewModel.isChangeWeightDialogShowing.collectAsState()
    val currentWeight by viewModel.currentWeight.collectAsState()
    val selectedWeight by viewModel.selectedWeight.collectAsState()
    
    val isBedTimeDialogShowing by viewModel.isChangeBedTimeDialogShowing.collectAsState()
    val currentBedTime by viewModel.currentBedTime.collectAsState()
    val bedTimeSelectedHour by viewModel.changeBedTimeDialogHour.collectAsState()
    val bedTimeSelectedMinute by viewModel.changeBedTimeDialogMinute.collectAsState()
    
    val isWakeupTimeDialogShowing by viewModel.isChangeWakeUpTimeDialogShowing.collectAsState()
    val currentWakeupTime by viewModel.currentWakeUpTime.collectAsState()
    val wakeupTimeSelectedHour by viewModel.changeWakeupTimeDialogHour.collectAsState()
    val wakeupTimeSelectedMinute by viewModel.changeWakeupTimeDialogMinute.collectAsState()
    
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
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .2f))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Heading Text
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                text = "Settings",
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_bold,
                        FontWeight.Bold
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // Reminders
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onRemindersClick()
                    }
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    imageVector = ImageVector.vectorResource(id = R.drawable.notification),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .weight(1f),
                    text = "Reminders",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_regular,
                            FontWeight.Normal
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        // General Settings
        SectionGeneralSettings(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            waterUnit = currentWaterUnit,
            weightUnit = currentWeightUnit,
            dailyIntakeGoal = currentDailyIntakeGoal,
            onUnitClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(ChangeUnitDialogEvent.ShowDialog)
            },
            onDailyIntakeClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(DailyIntakeGoalDialogEvent.ShowDialog)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Personal Settings
        SectionPersonalInformation(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            gender = currentGender,
            age = currentAge,
            weight = currentWeight,
            weightUnit = currentWeightUnit,
            bedTime = currentBedTime,
            wakeUpTime = currentWakeupTime,
            onGenderClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(ChangeGenderDialogEvent.ShowDialog)
            },
            onAgeClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(ChangeAgeDialogEvent.ShowDialog)
            },
            onWeightClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(ChangeWeightDialogEvent.ShowDialog)
            },
            onBedTimeClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(ChangeBedTimeDialogEvent.ShowDialog)
            },
            onWakeUpTimeClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(ChangeWakeupTimeDialogEvent.ShowDialog)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        /** Other Settings */
        SectionOtherSettings(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            onFaqClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onFaqClick()
            },
            onBugReportClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onBugReportClick()
            },
            onPrivacyPolicyClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                val url = Constants.PRIVACY_POLICY_URL
                if (url.startsWith("https://") || url.startsWith("http://")) {
                    val uriUrl = Uri.parse(url)
                    val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                    context.startActivity(launchBrowser)
                } else {
                    Toast.makeText(context, "Invalid Url", Toast.LENGTH_SHORT).show()
                }
                
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    
    // Change Unit Dialog
    if (isUnitDialogShowing) {
        ChangeUnitDialog(
            selectedWaterUnit = selectedWaterUnit,
            selectedWeightUnit = selectedWeightUnit,
            onWaterUnitChange = {
                viewModel.onEvent(ChangeUnitDialogEvent.ChangeSelectedUnit(waterUnit = it))
            },
            onWeightUnitChange = {
                viewModel.onEvent(ChangeUnitDialogEvent.ChangeSelectedUnit(weightUnit = it))
            },
            onConfirm = { viewModel.onEvent(ChangeUnitDialogEvent.SaveNewUnits) },
            onCancel = { viewModel.onEvent(ChangeUnitDialogEvent.DismissDialog) }
        )
    }
    
    // Daily Intake Goal Dialog
    if (isDailyIntakeGoalDialogShowing) {
        DailyIntakeGoalDialog(
            dailyIntakeGoal = selectedDailyIntakeGoal,
            currentWaterUnit = currentWaterUnit,
            onDailyIntakeGoalChange = {
                viewModel.onEvent(DailyIntakeGoalDialogEvent.OnDailyIntakeGoalChange(it))
            },
            onReset = { viewModel.onEvent(DailyIntakeGoalDialogEvent.OnReset) },
            onConfirm = { viewModel.onEvent(DailyIntakeGoalDialogEvent.SaveDailyIntakeGoal) },
            onCancel = { viewModel.onEvent(DailyIntakeGoalDialogEvent.DismissDialog) }
        )
    }
    
    // Change Gender Dialog
    if (isGenderDialogShowing) {
        ChangeGenderDialog(
            selectedGender = selectedGender,
            onGenderSelected = {
                viewModel.onEvent(ChangeGenderDialogEvent.SaveNewGender(it))
            },
            onCancel = {
                viewModel.onEvent(ChangeGenderDialogEvent.DismissDialog)
            }
        )
    }
    
    // Change Age Dialog
    if (isAgeDialogShowing) {
        ChangeAgeDialog(
            age = selectedAge,
            onCancel = { viewModel.onEvent(ChangeAgeDialogEvent.DismissDialog) },
            onConfirm = { viewModel.onEvent(ChangeAgeDialogEvent.SaveNewAge) },
            onAgeChange = { viewModel.onEvent(ChangeAgeDialogEvent.OnAgeChange(it)) }
        )
    }
    
    // Change Weight Dialog
    if (isWeightDialogShowing) {
        ChangeWeightDialog(
            weight = floor(selectedWeight).toInt(),
            onCancel = { viewModel.onEvent(ChangeWeightDialogEvent.DismissDialog) },
            onConfirm = { viewModel.onEvent(ChangeWeightDialogEvent.SaveNewWeight) },
            onWeightChange = { viewModel.onEvent(ChangeWeightDialogEvent.OnWeightChange(it.toFloat())) }
        )
    }
    
    // Change Bed Time Dialog
    if (isBedTimeDialogShowing) {
        ChangeTimeDialog(
            title = stringResource(id = R.string.bed_time),
            hour = bedTimeSelectedHour,
            minute = bedTimeSelectedMinute,
            onCancel = { viewModel.onEvent(ChangeBedTimeDialogEvent.DismissDialog) },
            onConfirm = { viewModel.onEvent(ChangeBedTimeDialogEvent.SaveNewBedTime) },
            onHourChange = { viewModel.onEvent(ChangeBedTimeDialogEvent.OnHourChange(it)) },
            onMinuteChange = { viewModel.onEvent(ChangeBedTimeDialogEvent.OnMinuteChange(it)) }
        )
    }
    
    // Change Wakeup Time Dialog
    if (isWakeupTimeDialogShowing) {
        ChangeTimeDialog(
            title = stringResource(id = R.string.wake_up_time),
            hour = wakeupTimeSelectedHour,
            minute = wakeupTimeSelectedMinute,
            onCancel = { viewModel.onEvent(ChangeWakeupTimeDialogEvent.DismissDialog) },
            onConfirm = { viewModel.onEvent(ChangeWakeupTimeDialogEvent.SaveNewWakeupTime) },
            onHourChange = { viewModel.onEvent(ChangeWakeupTimeDialogEvent.OnHourChange(it)) },
            onMinuteChange = { viewModel.onEvent(ChangeWakeupTimeDialogEvent.OnMinuteChange(it)) }
        )
    }
    
}