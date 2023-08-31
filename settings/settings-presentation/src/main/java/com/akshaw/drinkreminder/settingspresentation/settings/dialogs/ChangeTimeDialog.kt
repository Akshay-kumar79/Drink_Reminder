package com.akshaw.drinkreminder.settingspresentation.settings.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.shawnlin.numberpicker.NumberPicker

@Preview
@Composable
private fun ChangeTimeDialogPreview() {
    DrinkReminderTheme {
        ChangeTimeDialog(
            title = "Bed time",
            hour = Constants.BED_TIME_DEFAULT_HOUR,
            minute = Constants.BED_TIME_DEFAULT_MINUTE,
            onCancel = {},
            onConfirm = {},
            onHourChange = {},
            onMinuteChange = {},
        )
    }
}

@Composable
fun ChangeTimeDialog(
    modifier: Modifier = Modifier,
    title: String,
    hour: Int,
    minute: Int,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onHourChange: (hour: Int) -> Unit,
    onMinuteChange: (minute: Int) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onCancel()
        }
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp, start = 24.dp, top = 24.dp),
                text = title,
                fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                fontSize = 20.sp,
            )
    
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                // Hour selector
                AndroidView(
                    modifier = Modifier
                        .width(78.dp)
                        .height(128.dp),
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
                            fadingEdgeStrength = .1f
                            maxValue = 23
                            minValue = 0
                            selectedTextColor = context.getColor(R.color.on_background)
                            selectedTextSize = Utility.getFloatFromSp(context, 14f)
                            textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                            textSize = Utility.getFloatFromSp(context, 14f)
                            wheelItemCount = 3
                            value = hour
                            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular))
                            setOnValueChangedListener { picker, oldVal, newVal ->
                                onHourChange(newVal)
                            }
                        }
                    }
                )
                // Middle semicolon(":") between hour and minute selector
                AndroidView(
                    modifier = Modifier
                        .width(10.dp)
                        .height(128.dp),
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
                            wheelItemCount = 3
                            value = 100
                            typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                        }
                    }
                )
                // Minute selector
                AndroidView(
                    modifier = Modifier
                        .width(78.dp)
                        .height(128.dp),
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
                            fadingEdgeStrength = .1f
                            maxValue = 59
                            minValue = 0
                            selectedTextColor = context.getColor(R.color.on_background)
                            selectedTextSize = Utility.getFloatFromSp(context, 14f)
                            textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                            textSize = Utility.getFloatFromSp(context, 14f)
                            wheelItemCount = 3
                            value = minute
                            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular))
                            setOnValueChangedListener { picker, oldVal, newVal ->
                                onMinuteChange(newVal)
                            }
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onCancel()
                        }
                        .padding(4.dp),
                    text = stringResource(id = R.string.cancel),
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.width(36.dp))
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onConfirm()
                        }
                        .padding(4.dp),
                    text = "OK",
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}