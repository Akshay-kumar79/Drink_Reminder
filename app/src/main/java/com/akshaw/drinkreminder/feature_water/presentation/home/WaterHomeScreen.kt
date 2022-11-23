package com.akshaw.drinkreminder.feature_water.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.UiEvent
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.presentation.home.components.AddTrackableDrinkDialog
import com.akshaw.drinkreminder.feature_water.presentation.home.components.DrinkItem
import com.akshaw.drinkreminder.feature_water.presentation.home.components.TrackableDrinkSection
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Composable
fun WaterHomeScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: WaterHomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
                else -> Unit
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(WaterHomeEvent.OnReminderClick)
                    },
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.notification_solid),
                        contentDescription = "reminders"
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${viewModel.state.goal.roundToInt()} ${viewModel.state.waterUnit.name}",
                    fontSize = 44.sp,
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Completed: ${viewModel.state.progress.roundToInt()} ${viewModel.state.waterUnit.name}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium, FontWeight.Medium)),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))
                LinearProgressIndicator(
                    progress = (viewModel.state.progress / viewModel.state.goal).toFloat(),
                    modifier = Modifier
                        .padding(horizontal = 36.dp)
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(45.dp))
                )

                Spacer(modifier = Modifier.height(24.dp))

                TrackableDrinkSection(
                    modifier = Modifier.fillMaxWidth(),
                    context = context,
                    viewModel = viewModel
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 84.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        viewModel.onEvent(WaterHomeEvent.OnDrinkClick)
                    }
                ) {
                    Text(
                        text = "Drink Now",
                        fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            viewModel.onEvent(WaterHomeEvent.OnAddForgotDrinkClick)
                        }
                        .padding(4.dp),
                    text = "forgot to add?",
                    fontFamily = FontFamily(Font(R.font.roboto_bold, FontWeight.Bold)),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    text = "Today",
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

        }

        items(viewModel.state.drinks) { drink ->
            DrinkItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(vertical = 14.dp),
                drink = drink,
                viewModel = viewModel
            )
        }

    }
}











