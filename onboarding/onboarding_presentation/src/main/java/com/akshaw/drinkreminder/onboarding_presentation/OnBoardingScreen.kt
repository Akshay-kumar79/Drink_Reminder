package com.akshaw.drinkreminder.onboarding_presentation

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.onboarding_domain.utils.OnboardingPage
import com.akshaw.drinkreminder.onboarding_presentation.components.SelectAge
import com.akshaw.drinkreminder.onboarding_presentation.components.SelectBedTime
import com.akshaw.drinkreminder.onboarding_presentation.components.SelectGender
import com.akshaw.drinkreminder.onboarding_presentation.components.SelectWakeupTime
import com.akshaw.drinkreminder.onboarding_presentation.components.SelectWeight
import kotlinx.coroutines.flow.collectLatest
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.theme.composables.NotificationPermissionTextProvider
import com.akshaw.drinkreminder.corecompose.theme.composables.PermissionDialog
import com.akshaw.drinkreminder.onboarding_presentation.components.OnboardingGrantPermission
import com.akshaw.drinkreminder.onboarding_presentation.components.Permission
import com.akshaw.drinkreminder.corecompose.theme.events.NotificationPermissionDialogEvent
import com.akshaw.drinkreminder.onboarding_presentation.events.OnboardingEvent

@Composable
fun OnBoardingScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: OnboardingViewModel = hiltViewModel(),
    onProcessFinish: () -> Unit,
    shouldShowRequestPermissionRationale: (permission: String) -> Boolean,
    openAppSettings: () -> Unit,
    openAppSetExactAlarmPermissionSettings: () -> Unit
) {
    val context = LocalContext.current
    
    val state = viewModel.state
    
    
    val isReRequestNotificationPermDialogVisible by viewModel.isReRequestNotificationPermDialogVisible.collectAsState()
    
    var permissions by remember {
        mutableStateOf(emptyList<Permission>())
    }
    
    val hasNotificationPermission by viewModel.hasNotificationPermission.collectAsState()
    val hasExactAlarmPermission by viewModel.hasExactAlarmPermission.collectAsState()
    
    val notificationPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.onEvent(OnboardingEvent.OnPermissionResult(Manifest.permission.POST_NOTIFICATIONS, it))
    }
    
    val setHasPermissions = {
        val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        else
            true
        viewModel.onEvent(OnboardingEvent.ChangeHasNotificationPermission(notificationPermission))
        
        val exactAlarmPermission = if (Build.VERSION.SDK_INT in Build.VERSION_CODES.S..Build.VERSION_CODES.S_V2) {
            val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
            alarmManager?.canScheduleExactAlarms() ?: false
        } else {
            // On Android 13+ we are using USE_EXACT_ALARM
            true
        }
        viewModel.onEvent(OnboardingEvent.ChangeHasExactAlarmPermission(exactAlarmPermission))
    }
    
    // One time launch only when first compose
    LaunchedEffect(key1 = true) {
        // set hasNotificationPermission in viewModel
        setHasPermissions()
        
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                UiEvent.NavigateUp -> Unit
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }
                
                UiEvent.Success -> {
                    onProcessFinish()
                }
            }
        }
    }
    
    // Lifecycle callbacks
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                setHasPermissions()
            }
        }
        
        lifecycleOwner.lifecycle.addObserver(observer)
        
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    
    LaunchedEffect(key1 = hasNotificationPermission, key2 = hasExactAlarmPermission) {
        // setPermissions
        val perm = mutableListOf<Permission>()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            perm.add(Permission("Notification", Manifest.permission.POST_NOTIFICATIONS, hasNotificationPermission))
        
        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.S..Build.VERSION_CODES.S_V2)
            perm.add(Permission("Exact Alarm", Manifest.permission.SCHEDULE_EXACT_ALARM, hasExactAlarmPermission))
        
        permissions = perm
    }
    
    BackHandler(viewModel.state.page != OnboardingPage.GENDER) {
        viewModel.onEvent(OnboardingEvent.OnBackClick)
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.skip),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        viewModel.onEvent(OnboardingEvent.OnSkipClick)
                    }
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.height(54.dp))
            Text(
                text = when (state.page) {
                    OnboardingPage.GENDER -> stringResource(id = R.string.what_s_your_gender)
                    OnboardingPage.AGE -> stringResource(id = R.string.what_s_your_age)
                    OnboardingPage.WEIGHT -> stringResource(id = R.string.what_s_your_weight)
                    OnboardingPage.BED_TIME -> stringResource(id = R.string.what_s_your_bed_time)
                    OnboardingPage.WAKEUP_TIME -> stringResource(id = R.string.what_s_your_wake_up_time)
                    OnboardingPage.PERMISSION -> stringResource(id = R.string.grant_permission)
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.ubuntu_medium, FontWeight.Medium)),
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (state.page) {
                    OnboardingPage.GENDER -> stringResource(id = R.string.let_us_know_you_better)
                    OnboardingPage.AGE -> stringResource(id = R.string.let_us_know_you_for_better_goals)
                    OnboardingPage.WEIGHT -> stringResource(id = R.string.let_us_know_you_for_better_goals_calculation)
                    OnboardingPage.BED_TIME -> stringResource(id = R.string.let_us_know_you_for_better_reminder_settings)
                    OnboardingPage.WAKEUP_TIME -> stringResource(id = R.string.let_us_know_you_for_better_reminder_settings)
                    OnboardingPage.PERMISSION -> stringResource(id = R.string.onboarding_grant_permission_summary)
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            
            when (state.page) {
                OnboardingPage.GENDER -> {
                    SelectGender(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 54.dp, bottom = 104.dp)
                    )
                }
                
                OnboardingPage.AGE -> {
                    SelectAge(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 96.dp, bottom = 104.dp)
                    )
                }
                
                OnboardingPage.WEIGHT -> {
                    SelectWeight(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 96.dp, bottom = 104.dp)
                    )
                }
                
                OnboardingPage.BED_TIME -> {
                    SelectBedTime(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 96.dp, bottom = 104.dp)
                    )
                }
                
                OnboardingPage.WAKEUP_TIME -> {
                    SelectWakeupTime(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 96.dp, bottom = 104.dp)
                    )
                }
                
                OnboardingPage.PERMISSION -> {
                    OnboardingGrantPermission(
                        modifier = Modifier
                            .padding(top = 58.dp),
                        permissions = permissions,
                        onGrantPermissionClick = {
                            if (it.permission == Manifest.permission.POST_NOTIFICATIONS
                                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                            ) {
                                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else if (it.permission == Manifest.permission.SCHEDULE_EXACT_ALARM
                                && Build.VERSION.SDK_INT in Build.VERSION_CODES.S..Build.VERSION_CODES.S_V2
                            ) {
                                openAppSetExactAlarmPermissionSettings()
                            }
                        }
                    )
                }
            }
            
        }
        
        FloatingActionButton(
            onClick = {
                viewModel.onEvent(OnboardingEvent.OnNextClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp
            )
        }
        
        
    }
    
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
    
}
