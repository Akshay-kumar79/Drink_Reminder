package com.akshaw.drinkreminder.waterpresentation.reminders.dialogs

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.corecompose.utils.bounceClick
import com.akshaw.drinkreminder.waterpresentation.reminders.components.WeekDaySelector
import com.shawnlin.numberpicker.NumberPicker
import java.time.DayOfWeek


@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun UpsertReminderDialogPreview() {
    DrinkReminderTheme {
        ReminderBottomSheet(
            selectedDays = DayOfWeek.values().associateBy(
                { it.value },
                { it !in setOf(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY) }
            ),
            onCancel = {},
            onButtonClick = {},
            hour = 12,
            minute = 23,
            onHourChange = { },
            onMinuteChange = { },
            onDaySelectionChange = {  }
        )
    }
}

/**
 *  @param selectedDays: map with key as DayOfWeek.value and value as true or false (if day is selected or not).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertReminderDialog(
    selectedDays: Map<Int, Boolean>,
    onCancel: () -> Unit,
    onButtonClick: () -> Unit,
    hour: Int,
    minute: Int,
    onHourChange: (hour: Int) -> Unit,
    onMinuteChange: (minute: Int) -> Unit,
    onDaySelectionChange: (dayOfWeek: DayOfWeek) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(true)
    
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onCancel() },
        dragHandle = null
    ) {
        ReminderBottomSheet(
            selectedDays = selectedDays,
            onCancel = onCancel,
            onButtonClick = onButtonClick,
            hour = hour,
            minute = minute,
            onHourChange = onHourChange,
            onMinuteChange = onMinuteChange,
            onDaySelectionChange = onDaySelectionChange
        )
    }
}

/**
 *  @param selectedDays: map with key as DayOfWeek.value and value as true or false (if day is selected or not).
 */
@Suppress("MagicNumber")
@Composable
private fun ReminderBottomSheet(
    selectedDays: Map<Int, Boolean>,
    onCancel: () -> Unit,
    onButtonClick: () -> Unit,
    hour: Int,
    minute: Int,
    onHourChange: (hour: Int) -> Unit,
    onMinuteChange: (minute: Int) -> Unit,
    onDaySelectionChange: (dayOfWeek: DayOfWeek) -> Unit
) {
    val view = LocalView.current
    
    Box(
        modifier = Modifier
            .padding(24.dp),
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    onCancel()
                }
                .padding(8.dp)
                .align(Alignment.TopEnd),
            imageVector = ImageVector.vectorResource(id = R.drawable.cross_delete_icon),
            contentDescription = "close",
            tint = MaterialTheme.colorScheme.primary
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Set Reminder",
                fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                AndroidView(
                    modifier = Modifier
                        .width(78.dp)
                        .height(175.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            formatter = NumberPicker.Formatter {
                                if (it in 0..9) {
                                    "0$it"
                                } else {
                                    "$it"
                                }
                            }
                            setDividerThickness(0)
                            fadingEdgeStrength = .5f
                            maxValue = 23
                            minValue = 0
                            selectedTextColor = context.getColor(R.color.on_background)
                            selectedTextSize = Utility.getFloatFromSp(context, 14f)
                            textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                            textSize = Utility.getFloatFromSp(context, 14f)
                            wheelItemCount = 5
                            value = hour
                            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular))
                            setOnValueChangedListener { _, _, newVal ->
                                onHourChange(newVal)
                            }
                        }
                    }
                )
                AndroidView(
                    modifier = Modifier
                        .width(10.dp)
                        .height(175.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            formatter = NumberPicker.Formatter { ":" }
                            setDividerThickness(0)
                            fadingEdgeStrength = 1f
                            maxValue = 100
                            minValue = 100
                            selectedTextColor = context.getColor(R.color.on_background)
                            selectedTextSize = Utility.getFloatFromSp(context, 14f)
                            textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                            textSize = Utility.getFloatFromSp(context, 14f)
                            wheelItemCount = 5
                            value = 100
                            typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                        }
                    }
                )
                AndroidView(
                    modifier = Modifier
                        .width(78.dp)
                        .height(175.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            formatter = NumberPicker.Formatter {
                                if (it in 0..9) {
                                    "0$it"
                                } else {
                                    "$it"
                                }
                            }
                            setDividerThickness(0)
                            fadingEdgeStrength = .5f
                            maxValue = 59
                            minValue = 0
                            selectedTextColor = context.getColor(R.color.on_background)
                            selectedTextSize = Utility.getFloatFromSp(context, 14f)
                            textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                            textSize = Utility.getFloatFromSp(context, 14f)
                            wheelItemCount = 5
                            value = minute
                            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular))
                            setOnValueChangedListener { _, _, newVal ->
                                onMinuteChange(newVal)
                            }
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.repeat),
                fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            WeekDaySelector(
                modifier = Modifier.fillMaxWidth(),
                selectedDays = selectedDays,
                onDaySelectionChange = onDaySelectionChange
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            FloatingActionButton(
                onClick = {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    onButtonClick()
                },
                modifier = Modifier
                    .bounceClick()
                    .fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = stringResource(id = R.string.done),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}