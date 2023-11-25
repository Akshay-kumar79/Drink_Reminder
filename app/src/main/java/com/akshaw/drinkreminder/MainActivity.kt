package com.akshaw.drinkreminder

import android.app.Activity
import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
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
import com.akshaw.drinkreminder.navigation.Route
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.onboarding_presentation.OnBoardingScreen
import com.akshaw.drinkreminder.settingspresentation.bug_report.SettingsBugReportScreen
import com.akshaw.drinkreminder.settingspresentation.faq.SettingsFaqScreen
import com.akshaw.drinkreminder.ui.Animations
import com.akshaw.drinkreminder.ui.presentation.MainScreen
import com.akshaw.drinkreminder.ui.presentation.components.BottomNavigationBar
import com.akshaw.drinkreminder.waterpresentation.reminders.WaterReminderScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var preferences: Preferences
    
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launch {
            val isOnboardingCompleted = preferences.getIsOnboardingCompleted().first()
            
            setContent {
                DrinkReminderTheme {
                    val snackbarHostState = remember { SnackbarHostState() }
                    val navController = rememberNavController()
                    
                    val currentBottomNavScreen = rememberPagerState { 3 }
                    
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                currentBottomNavScreen = currentBottomNavScreen
                            )
                        }
                    ) {
                        NavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .background(MaterialTheme.colorScheme.background),
                            enterTransition = { Animations.Default.enter() },
                            popEnterTransition = { Animations.Default.enter() },
                            exitTransition = { Animations.Default.exit() },
                            popExitTransition = { Animations.Default.exit() },
                            navController = navController,
                            startDestination = if (isOnboardingCompleted) Route.MainScreen.route else Route.OnboardingScreen.route
                        ) {
                            composable(route = Route.OnboardingScreen.route) {
                                OnBoardingScreen(
                                    snackbarHostState = snackbarHostState,
                                    onProcessFinish = {
                                        navController.navigate(Route.MainScreen.route) {
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
                                route = Route.MainScreen.route,
                                exitTransition = {
                                    when (this.targetState.destination.route) {
                                        Route.WaterReminderScreen.route -> {
                                            Animations.AppHorizontalSlide.exit(this)
                                        }
                                        Route.WaterADayDrinkScreen.route
                                                + "/{${WaterADayDrinkViewModel.CURRENT_DAY_ARGUMENT}}" -> {
                                            Animations.AppVerticalSlide.exit(this)
                                        }
                                        else -> Animations.Default.exit()
                                    }
                                },
                                popEnterTransition = {
                                    when (this.initialState.destination.route) {
                                        Route.WaterReminderScreen.route -> {
                                            Animations.AppHorizontalSlide.popEnter(this)
                                        }
                                        Route.WaterADayDrinkScreen.route
                                                + "/{${WaterADayDrinkViewModel.CURRENT_DAY_ARGUMENT}}" -> {
                                            Animations.AppVerticalSlide.popEnter(this)
                                        }
                                        else -> Animations.Default.enter()
                                    }
                                },
                            ) {
                                BackHandler {
                                    lifecycleScope.launch {
                                        if (currentBottomNavScreen.currentPage != 0)
                                            currentBottomNavScreen.scrollToPage(0)
                                        else {
                                            finish()
                                        }
                                    }
                                }
                                MainScreen(
                                    currentBottomNavScreen = currentBottomNavScreen,
                                    snackbarHostState = snackbarHostState,
                                    navController = navController
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
                                    if (this.initialState.destination.route == Route.MainScreen.route) {
                                        Animations.AppVerticalSlide.enter(this)
                                    } else Animations.Default.enter()
                                },
                                popExitTransition = {
                                    if (this.targetState.destination.route == Route.MainScreen.route) {
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
                                route = Route.WaterReminderScreen.route,
                                enterTransition = {
                                    if (this.initialState.destination.route == Route.MainScreen.route) {
                                        Animations.AppHorizontalSlide.enter(this)
                                    } else Animations.Default.enter()
                                },
                                popExitTransition = {
                                    if (this.targetState.destination.route == Route.MainScreen.route) {
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
