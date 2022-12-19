package com.akshaw.drinkreminder.ui

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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akshaw.drinkreminder.feature_water.presentation.a_day_drink.WaterADayDrinkScreen
import com.akshaw.drinkreminder.feature_water.presentation.a_day_drink.WaterADayDrinkViewModel
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeScreen
import com.akshaw.drinkreminder.feature_water.presentation.report.WaterReportScreen
import com.akshaw.drinkreminder.navigation.Route
import com.akshaw.drinkreminder.ui.theme.DrinkReminderTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkReminderTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(MaterialTheme.colorScheme.background)
                    )
                    
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = Route.WaterReportScreen.route) {
                        
                        composable(route = Route.WaterHomeScreen.route){
                            WaterHomeScreen(snackbarHostState = snackbarHostState)
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
                    }

//                        OnBoardingScreen(
//                            snackbarHostState = snackbarHostState,
//                            onProcessFinish = {
//                                lifecycleScope.launch {
//                        snackbarHostState.currentSnackbarData?.dismiss()
//                                    snackbarHostState.showSnackbar(
//                                        message = "Navigating to home...",
//                                        duration = SnackbarDuration.Short
//                                    )
//                                }
//                            }
//                        )

//                        WaterHomeScreen(snackbarHostState = snackbarHostState)

//                        WaterReportScreen()

//                    WaterADayDrinkScreen(snackbarHostState) {
//
//                    }
                }
            }
        }
    }
}
