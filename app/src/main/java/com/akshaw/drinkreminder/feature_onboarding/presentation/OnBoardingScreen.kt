package com.akshaw.drinkreminder.feature_onboarding.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.feature_onboarding.presentation.components.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnBoardingScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: OnboardingViewModel = hiltViewModel(),
    onProcessFinish: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
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

}
