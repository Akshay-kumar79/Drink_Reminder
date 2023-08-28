package com.akshaw.drinkreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import com.akshaw.drinkreminder.ui.presentation.components.BottomNavigationBar
import com.akshaw.drinkreminder.waterpresentation.reminders.WaterReminderScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        startDestination = Route.WaterHomeScreen.route
                    ) {
                        
                        composable(route = Route.OnboardingScreen.route) {
                            OnBoardingScreen(
                                snackbarHostState = snackbarHostState,
                                onProcessFinish = {
                                    lifecycleScope.launch {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            message = "Navigating to home...",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        }
                        
                        composable(route = Route.WaterHomeScreen.route) {
                            WaterHomeScreen(
                                snackbarHostState = snackbarHostState,
                                onReminderClick = {
                                    navController.navigate(Route.WaterReminderScreen.route)
                                }
                            )
                        }
                        
                        composable(route = Route.WaterReportScreen.route) {
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
                            )
                        ) {
                            WaterADayDrinkScreen(
                                snackbarHostState = snackbarHostState,
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        
                        composable(route = Route.SettingsScreen.route) {
                            SettingsScreen(
                                snackbarHostState = snackbarHostState,
                                onRemindersClick = {
                                    navController.navigate(Route.WaterReminderScreen.route)
                                },
                                onFaqClick = {
                                    navController.navigate(Route.SettingsFaqScreen.route)
                                },
                                onBugReportClick = {
                                    navController.navigate(Route.SettingsBugReportScreen.route)
                                }
                            )
                        }
                        
                        composable(route = Route.WaterReminderScreen.route) {
                            WaterReminderScreen(
                                snackbarHostState = snackbarHostState,
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        
                        composable(route = Route.SettingsFaqScreen.route) {
                            SettingsFaqScreen(
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        
                        composable(route = Route.SettingsBugReportScreen.route) {
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
