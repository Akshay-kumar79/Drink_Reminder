package com.akshaw.drinkreminder.ui.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.navigation.Route

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val navBarScreens = listOf(
        Route.WaterHomeScreen,
        Route.WaterReportScreen,
        Route.SettingsScreen
    )
    
    AnimatedContent(
        targetState = currentDestination?.route in navBarScreens.map { it.route },
        transitionSpec = {
            slideInVertically { it } togetherWith
                    slideOutVertically { it }
        },
        label = ""
    ) { isVisible ->
        
        if (isVisible)
            NavigationBar {
                
                AddNavigationBarItem(
                    label = "Home",
                    iconResId = R.drawable.bottom_nav_home,
                    destinationRoute = Route.WaterHomeScreen,
                    currentDestination = currentDestination,
                    navController = navController
                )
                
                AddNavigationBarItem(
                    label = "Report",
                    iconResId = R.drawable.bottom_nav_report,
                    destinationRoute = Route.WaterReportScreen,
                    currentDestination = currentDestination,
                    navController = navController
                )
                
                AddNavigationBarItem(
                    label = "Settings",
                    iconResId = R.drawable.bottom_nav_settings,
                    destinationRoute = Route.SettingsScreen,
                    currentDestination = currentDestination,
                    navController = navController
                )
                
            }
    }
    
}

@Composable
private fun RowScope.AddNavigationBarItem(
    label: String,
    iconResId: Int,
    destinationRoute: Route,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = label)
        },
        icon = {
            Icon(imageVector = ImageVector.vectorResource(id = iconResId), contentDescription = null)
        },
        selected = currentDestination?.route == destinationRoute.route,
        onClick = {
            navController.navigate(destinationRoute.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}