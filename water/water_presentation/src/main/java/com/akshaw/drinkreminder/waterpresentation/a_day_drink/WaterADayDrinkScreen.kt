package com.akshaw.drinkreminder.waterpresentation.a_day_drink

import android.view.SoundEffectConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.akshaw.drinkreminder.corecompose.utils.bounceClick
import com.akshaw.drinkreminder.waterpresentation.common.components.DialogAddForgottenDrink
import com.akshaw.drinkreminder.waterpresentation.common.components.DrinkItem
import com.akshaw.drinkreminder.waterpresentation.common.events.DialogAddForgottenDrinkEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun WaterADayDrinkScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: WaterADayDrinkViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val view = LocalView.current
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // screen state
    val todayDrinks by viewModel.todayDrinks.collectAsState()
    val waterUnit by viewModel.waterUnit.collectAsState()
    
    // add forgotten drink dialog state
    val isAddForgottenDrinkDialogShowing by viewModel.isAddForgottenDrinkDialogShowing.collectAsState()
    val addForgottenDrinkDialogQuantity by viewModel.addForgottenDrinkDialogQuantity.collectAsState()
    val addForgottenDrinkDialogHour by viewModel.addForgottenDrinkDialogHour.collectAsState()
    val addForgottenDrinkDialogMinute by viewModel.addForgottenDrinkDialogMinute.collectAsState()
    
    
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
    
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onBackClicked()
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.left_arrow_icon),
                        contentDescription = null
                    )
                }
                
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Report",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = when (viewModel.currentDate) {
                                LocalDate.now() -> {
                                    "Today"
                                }
                                LocalDate.now().minusDays(1) -> {
                                    "Yesterday"
                                }
                                else -> {
                                    viewModel.currentDate.format(
                                        DateTimeFormatter.ofPattern("dd LLL, yyyy", Locale.ENGLISH)
                                    )
                                }
                            },
                            fontSize = 14.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.ubuntu_regular,
                                    FontWeight.Normal
                                )
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    
                }
                
                items(todayDrinks, key = { it.id }) { drink ->
                    DrinkItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
        
                            }
                            .padding(vertical = 14.dp),
                        drink = drink,
                        waterUnit = waterUnit,
                        onDeleteClick = {
                            viewModel.onEvent(WaterADayDrinkEvent.OnDrinkDeleteClick(drink))
                            snackbarHostState.currentSnackbarData?.dismiss()
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Drink Removed Successfully",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )
                                
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(WaterADayDrinkEvent.RestoreDrink)
                                }
                            }
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(88.dp))
                }
            }
        }
        
        FloatingActionButton(
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(DialogAddForgottenDrinkEvent.OnAddForgotDrinkClick)
            },
            modifier = Modifier
                .bounceClick()
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                contentDescription = null
            )
        }
        
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
        
    }
    
}