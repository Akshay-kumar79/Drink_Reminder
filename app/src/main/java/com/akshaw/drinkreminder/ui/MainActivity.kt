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
import androidx.lifecycle.lifecycleScope
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnBoardingScreen
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeScreen
import com.akshaw.drinkreminder.ui.theme.DrinkReminderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    ) {

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

                        WaterHomeScreen(snackbarHostState = snackbarHostState)
                    }
                }
            }
        }
    }
}
