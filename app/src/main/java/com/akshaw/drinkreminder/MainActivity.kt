package com.akshaw.drinkreminder

import android.app.Activity
import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.waterpresentation.a_day_drink.WaterADayDrinkScreen
import com.akshaw.drinkreminder.waterpresentation.a_day_drink.WaterADayDrinkViewModel
import com.akshaw.drinkreminder.waterpresentation.home.WaterHomeScreen
import com.akshaw.drinkreminder.waterpresentation.report.WaterReportScreen
import com.akshaw.drinkreminder.navigation.Route
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.onboarding_presentation.OnBoardingScreen
import com.akshaw.drinkreminder.settingspresentation.bug_report.SettingsBugReportScreen
import com.akshaw.drinkreminder.settingspresentation.faq.SettingsFaqScreen
import com.akshaw.drinkreminder.settingspresentation.settings.SettingsScreen
import com.akshaw.drinkreminder.ui.Animations
import com.akshaw.drinkreminder.ui.presentation.components.BottomNavigationBar
import com.akshaw.drinkreminder.waterpresentation.reminders.WaterReminderScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var preferences: Preferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launch {
            val isOnboardingCompleted = preferences.getIsOnboardingCompleted().first()
            
            setContent {
                DrinkReminderTheme {
                    val snackbarHostState = remember { SnackbarHostState() }
                    val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }
                    ) {
                        NavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .background(MaterialTheme.colorScheme.background),
                            navController = navController,
                            startDestination = if (isOnboardingCompleted) Route.WaterHomeScreen.route else Route.OnboardingScreen.route
                        ) {
                            
                            composable(route = Route.OnboardingScreen.route) {
                                OnBoardingScreen(
                                    snackbarHostState = snackbarHostState,
                                    onProcessFinish = {
                                        navController.navigate(Route.WaterHomeScreen.route) {
                                            popUpTo(Route.OnboardingScreen.route) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    shouldShowRequestPermissionRationale = ::shouldShowRequestPermissionRationale,
                                    openAppSettings = ::openAppSettings,
                                    openAppSetExactAlarmPermissionSettings = ::openAppSetExactAlarmPermissionSettings
                                )
                            }
                            
                            composable(
                                route = Route.WaterHomeScreen.route,
                                exitTransition = {
                                    if (this.targetState.destination.route == Route.WaterReminderScreen.route) {
                                        Animations.AppHorizontalSlide.exit(this)
                                    } else Animations.Default.exit()
                                },
                                popEnterTransition = {
                                    if (this.initialState.destination.route == Route.WaterReminderScreen.route) {
                                        Animations.AppHorizontalSlide.popEnter(this)
                                    } else Animations.Default.enter()
                                }
                            ) {
                                WaterHomeScreen(
                                    snackbarHostState = snackbarHostState,
                                    onReminderClick = {
                                        lifecycleScope.launch {
                                            navController.navigate(Route.WaterReminderScreen.route)
                                        }
                                    }
                                )
                            }
                            
                            composable(
                                route = Route.WaterReportScreen.route,
                                exitTransition = {
                                    if (this.targetState.destination.route == Route.WaterADayDrinkScreen.route + "/{${WaterADayDrinkViewModel.CURRENT_DAY_ARGUMENT}}") {
                                        Animations.AppVerticalSlide.exit(this)
                                    } else Animations.Default.exit()
                                },
                                popEnterTransition = {
                                    if (this.initialState.destination.route == Route.WaterADayDrinkScreen.route + "/{${WaterADayDrinkViewModel.CURRENT_DAY_ARGUMENT}}") {
                                        Animations.AppVerticalSlide.popEnter(this)
                                    } else Animations.Default.enter()
                                }
                            ) {
                                WaterReportScreen(
                                    onADayDrinkClick = { date ->
                                        navController.navigate(Route.WaterADayDrinkScreen.route + "/" + date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                                    }
                                )
                            }
                            
                            composable(
                                route = Route.WaterADayDrinkScreen.route + "/{${WaterADayDrinkViewModel.CURRENT_DAY_ARGUMENT}}",
                                arguments = listOf(
                                    navArgument(WaterADayDrinkViewModel.CURRENT_DAY_ARGUMENT) {
                                        type = NavType.StringType
                                        defaultValue = LocalDate.now().toString()
                                    }
                                ),
                                enterTransition = {
                                    if (this.initialState.destination.route == Route.WaterReportScreen.route) {
                                        Animations.AppVerticalSlide.enter(this)
                                    } else Animations.Default.enter()
                                },
                                popExitTransition = {
                                    if (this.targetState.destination.route == Route.WaterReportScreen.route) {
                                        Animations.AppVerticalSlide.popExit(this)
                                    } else Animations.Default.exit()
                                }
                            ) {
                                WaterADayDrinkScreen(
                                    snackbarHostState = snackbarHostState,
                                    onBackClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            
                            composable(
                                route = Route.SettingsScreen.route,
                                exitTransition = {
                                    if (this.targetState.destination.route == Route.WaterReminderScreen.route) {
                                        Animations.AppHorizontalSlide.exit(this)
                                    } else Animations.Default.exit()
                                },
                                popEnterTransition = {
                                    if (this.initialState.destination.route == Route.WaterReminderScreen.route) {
                                        Animations.AppHorizontalSlide.popEnter(this)
                                    } else Animations.Default.enter()
                                }
                            ) {
                                SettingsScreen(
                                    snackbarHostState = snackbarHostState,
                                    onRemindersClick = {
                                        lifecycleScope.launch {
                                            navController.navigate(Route.WaterReminderScreen.route)
                                        }
                                    },
                                    onFaqClick = {
                                        navController.navigate(Route.SettingsFaqScreen.route)
                                    },
                                    onBugReportClick = {
                                        navController.navigate(Route.SettingsBugReportScreen.route)
                                    }
                                )
                            }
                            
                            composable(
                                route = Route.WaterReminderScreen.route,
                                enterTransition = {
                                    if (this.initialState.destination.route in setOf(Route.WaterHomeScreen.route, Route.SettingsScreen.route)) {
                                        Animations.AppHorizontalSlide.enter(this)
                                    } else Animations.Default.enter()
                                },
                                popExitTransition = {
                                    if (this.targetState.destination.route in setOf(Route.WaterHomeScreen.route, Route.SettingsScreen.route)) {
                                        Animations.AppHorizontalSlide.popExit(this)
                                    } else Animations.Default.exit()
                                }
                            ) {
                                WaterReminderScreen(
                                    snackbarHostState = snackbarHostState,
                                    onBackClicked = {
                                        navController.popBackStack()
                                    },
                                    shouldShowRequestPermissionRationale = ::shouldShowRequestPermissionRationale,
                                    openAppSettings = ::openAppSettings,
                                    openAppSetExactAlarmPermissionSettings = ::openAppSetExactAlarmPermissionSettings
                                )
                            }
                            
                            composable(route = Route.SettingsFaqScreen.route) {
                                SettingsFaqScreen(
                                    onBackClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            
                            composable(
                                route = Route.SettingsBugReportScreen.route
                            ) {
                                SettingsBugReportScreen(
                                    onBackClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                        
                        
                    }
                }
            }
            
        }
    }
}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

private fun Activity.openAppSetExactAlarmPermissionSettings() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
        if (alarmManager?.canScheduleExactAlarms() == false) {
            Intent().also { intent ->
                intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                startActivity(intent)
            }
        }
    }
}
