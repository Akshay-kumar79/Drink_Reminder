package com.akshaw.drinkreminder.waterpresentation.reminders.components

import android.util.Log
import android.widget.PopupWindow
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.formatted24HourTime
import com.akshaw.drinkreminder.core.util.formattedString
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.waterdomain.model.DrinkReminder
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

@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    drinkReminder: DrinkReminder,
    onClick: (drinkReminder: DrinkReminder) -> Unit,
    onSwitchCheckChange: (drinkReminder: DrinkReminder, isReminderOn: Boolean) -> Unit,
    onDeleteReminder: (drinkReminder: DrinkReminder) -> Unit
) {
    
    var isPopupShowing by remember { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    
    val density = LocalDensity.current
    
    Row(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        isPopupShowing = true
                    },
                    onTap = {
                        onClick(drinkReminder)
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
                onSwitchCheckChange(drinkReminder, it)
            }
        )
        Spacer(modifier = Modifier.width(6.dp))
        
    }
    
}