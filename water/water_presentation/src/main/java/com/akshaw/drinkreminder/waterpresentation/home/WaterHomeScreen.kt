package com.akshaw.drinkreminder.waterpresentation.home

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.uievents.UiEvent
import com.akshaw.drinkreminder.waterpresentation.common.components.DialogAddForgottenDrink
import com.akshaw.drinkreminder.waterpresentation.common.components.DrinkItem
import com.akshaw.drinkreminder.waterpresentation.home.components.TrackableDrinkSection
import com.akshaw.drinkreminder.waterpresentation.common.events.DialogAddForgottenDrinkEvent
import com.akshaw.drinkreminder.waterpresentation.home.events.WaterHomeEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun WaterHomeScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: WaterHomeViewModel = hiltViewModel(),
    onReminderClick: () -> Unit
) {
    
    val context = LocalContext.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    
    // Screen State
    val goal by viewModel.dailyIntakeGoal.collectAsState()
    val waterUnit by viewModel.waterUnit.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val todayDrinks by viewModel.todayDrinks.collectAsState()
    val trackableDrinks by viewModel.trackableDrinks.collectAsState()
    val selectedTrackableDrink by viewModel.selectedTrackableDrink.collectAsState()
    
    // Add Forgotten Drink Dialog State
    val isAddForgottenDrinkDialogShowing by viewModel.isAddForgottenDrinkDialogShowing.collectAsState()
    val addForgottenDrinkDialogQuantity by viewModel.addForgottenDrinkDialogQuantity.collectAsState()
    val addForgottenDrinkDialogHour by viewModel.addForgottenDrinkDialogHour.collectAsState()
    val addForgottenDrinkDialogMinute by viewModel.addForgottenDrinkDialogMinute.collectAsState()
    
    // Add Trackable Drink Dialog State
    val isAddTrackableDrinkDialogShowing by viewModel.isAddTrackableDrinkDialogShowing.collectAsState()
    val addTrackableDrinkDialogQuantity by viewModel.addTrackableDrinkDialogQuantity.collectAsState()
    
    // Remove Trackable Drink Dialog State
    val isRemoveTrackableDrinkDialogShowing by viewModel.isRemoveTrackableDrinkDialogShowing.collectAsState()
    
    
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
                else -> Unit
            }
        }
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onReminderClick()
                    },
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            end = 8.dp
                        )
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.notification_solid),
                        contentDescription = "reminders"
                    )
                }
                
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${floor(goal).toInt()} ${waterUnit.text}",
                    fontSize = 44.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Completed: ${progress.roundToInt()} ${waterUnit.text}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_medium,
                            FontWeight.Medium
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                LinearProgressIndicator(
                    progress = (progress / goal).toFloat(),
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
                    viewModel = viewModel,
                    trackableDrinks = trackableDrinks,
                    selectedTrackableDrink = selectedTrackableDrink,
                    waterUnit = waterUnit,
                    isAddTrackableDrinkDialogShowing = isAddTrackableDrinkDialogShowing,
                    addTrackableDrinkDialogQuantity = addTrackableDrinkDialogQuantity,
                    isRemoveTrackableDrinkDialogShowing = isRemoveTrackableDrinkDialogShowing
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 84.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        viewModel.onEvent(WaterHomeEvent.OnDrinkClick)
                    }) {
                    Text(
                        text = "Drink Now",
                        fontFamily = FontFamily(
                            Font(
                                R.font.ubuntu_bold,
                                FontWeight.Bold
                            )
                        ),
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
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            viewModel.onEvent(DialogAddForgottenDrinkEvent.OnAddForgotDrinkClick)
                        }
                        .padding(4.dp),
                    text = "forgot to add?",
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_bold,
                            FontWeight.Bold
                        )
                    ),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground)
                
                DialogAddForgottenDrink(
                    isDialogShowing = isAddForgottenDrinkDialogShowing,
                    quantity = addForgottenDrinkDialogQuantity,
                    hour = addForgottenDrinkDialogHour,
                    minute = addForgottenDrinkDialogMinute,
                    onConfirm = {
                        viewModel.onEvent(DialogAddForgottenDrinkEvent.OnConfirmClick)
                    },
                    onCancel = {
                        viewModel.onEvent(DialogAddForgottenDrinkEvent.OnDismiss)
                    },
                    onQuantityChange = {
                        viewModel.onEvent(DialogAddForgottenDrinkEvent.OnQuantityAmountChange(it))
                    },
                    onHourChange = {
                        viewModel.onEvent(DialogAddForgottenDrinkEvent.OnHourChange(it))
                    },
                    onMinuteChange = {
                        viewModel.onEvent(DialogAddForgottenDrinkEvent.OnMinuteChange(it))
                    })
                
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "Today",
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
        }
        
        itemsIndexed(todayDrinks) { _, drink ->
            DrinkItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(vertical = 14.dp),
                drink = drink,
                waterUnit = waterUnit,
                onDeleteClick = {
                    viewModel.onEvent(WaterHomeEvent.OnDrinkDeleteClick(drink))
                    snackbarHostState.currentSnackbarData?.dismiss()
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Drink Removed Successfully",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Long
                        )
                        
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(WaterHomeEvent.RestoreDrink)
                        }
                    }
                }
            )
        }
        
    }
}