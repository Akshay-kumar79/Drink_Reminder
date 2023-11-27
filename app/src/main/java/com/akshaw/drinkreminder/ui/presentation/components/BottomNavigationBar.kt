package com.akshaw.drinkreminder.ui.presentation.components

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akshaw.drinkreminder.navigation.BottomNavRoute
import com.akshaw.drinkreminder.navigation.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentBottomNavScreen: PagerState,
) {
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val screens = listOf(
        BottomNavRoute.WaterHomeScreen,
        BottomNavRoute.WaterReportScreen,
        BottomNavRoute.SettingsScreen
    )
    
    AnimatedContent(
        targetState = currentDestination?.route == Route.MainScreen.route,
        transitionSpec = {
            slideInVertically { it } togetherWith
                    slideOutVertically { it }
        },
        label = ""
    ) { isVisible ->
        
        if (isVisible)
            NavigationBar {
                
                screens.forEach {
                    AddNavigationBarItem(
                        screen = it,
                        currentBottomNavScreen = currentBottomNavScreen
                    )
                }
            }
    }
    
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RowScope.AddNavigationBarItem(
    screen: BottomNavRoute,
    currentBottomNavScreen: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    
    val onClick = {
        coroutineScope.launch {
            currentBottomNavScreen.animateScrollToPage(screen.page)
        }
        Unit
    }
    Box(
        modifier = Modifier
            .weight(1f)
    ) {
        Row {
            NavigationBarItem(
                modifier = Modifier,
                label = {
                    Text(
                        fontSize = 13.sp,
                        text = screen.title
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = ImageVector.vectorResource(id = screen.iconResId),
                        contentDescription = null
                    )
                },
                interactionSource = interactionSource,
                selected = currentBottomNavScreen.currentPage == screen.page,
                onClick = onClick
            )
        }
        
        // Ripple Effect
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                enabled = true,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = {
                    onClick()
                    Log.v("MYTAG", "clicked")
                }
            )
        )
    }
    
}