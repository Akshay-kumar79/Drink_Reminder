package com.akshaw.drinkreminder.ui.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.akshaw.drinkreminder.navigation.Route
import com.akshaw.drinkreminder.settingspresentation.settings.SettingsScreen
import com.akshaw.drinkreminder.waterpresentation.home.WaterHomeScreen
import com.akshaw.drinkreminder.waterpresentation.report.WaterReportScreen
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    currentBottomNavScreen: PagerState,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxSize(),
        state = currentBottomNavScreen,
        userScrollEnabled = false,
    ) { page ->
        when (page) {
            0 -> WaterHomeScreen(
                snackbarHostState = snackbarHostState,
                onReminderClick = {
                    navController.navigate(Route.WaterReminderScreen.route)
                }
            )
            
            1 -> WaterReportScreen(
                onADayDrinkClick = { date ->
                    navController.navigate(Route.WaterADayDrinkScreen.route + "/" + date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                }
            )
            
            2 -> SettingsScreen(
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
    }
}