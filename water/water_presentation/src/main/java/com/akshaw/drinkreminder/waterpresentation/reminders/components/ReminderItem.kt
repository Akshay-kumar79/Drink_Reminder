package com.akshaw.drinkreminder.waterpresentation.reminders.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.formatted24HourTime
import com.akshaw.drinkreminder.core.util.formattedString
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime

@Preview
@Composable
private fun ReminderItemPreview() {
    DrinkReminderTheme {
        ReminderItem(
            modifier = Modifier
                .background(color = Color.White),
            drinkReminder = DrinkReminder(
                0,
                LocalTime.now(),
                true,
                listOf(DayOfWeek.SATURDAY, DayOfWeek.FRIDAY)
            ),
            onClick = {},
            onSwitchCheckChange = { _, _ -> },
            onDeleteReminder = { }
        )
    }
}

@Suppress("MagicNumber")
@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    drinkReminder: DrinkReminder,
    onClick: (drinkReminder: DrinkReminder) -> Unit,
    onSwitchCheckChange: (drinkReminder: DrinkReminder, isReminderOn: Boolean) -> Unit,
    onDeleteReminder: (drinkReminder: DrinkReminder) -> Unit
) {
    
    val view = LocalView.current
    val hapticFeedback = LocalHapticFeedback.current
    
    var isPopupShowing by remember { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    
    val density = LocalDensity.current
    
    val updatedDrinkReminder by rememberUpdatedState(drinkReminder)
    
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        isPopupShowing = true
                    },
                    onTap = {
                        coroutineScope.launch {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            interactionSource.emit(PressInteraction.Release(press))
                            
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onClick(updatedDrinkReminder)
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                }
        ) {
            
            Text(
                text = drinkReminder.time.formatted24HourTime(),
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Text(
                text = drinkReminder.activeDays.formattedString(),
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_regular,
                        FontWeight.Normal
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            DropdownMenu(
                modifier = Modifier
                    .width(100.dp),
                expanded = isPopupShowing,
                onDismissRequest = { isPopupShowing = false },
                offset = pressOffset.copy(
                    x = pressOffset.x - 100.dp,
                    y = pressOffset.y - itemHeight
                )
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.delete),
                            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onDeleteReminder(drinkReminder)
                        isPopupShowing = false
                    }
                )
            }
        }
        
        Switch(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .scale(.65f),
            checked = drinkReminder.isReminderOn,
            onCheckedChange = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onSwitchCheckChange(drinkReminder, it)
            }
        )
        Spacer(modifier = Modifier.width(6.dp))
        
    }
    
}