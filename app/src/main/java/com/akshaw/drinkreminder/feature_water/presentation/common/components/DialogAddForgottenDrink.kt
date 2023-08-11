package com.akshaw.drinkreminder.feature_water.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Utility
import com.shawnlin.numberpicker.NumberPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddForgottenDrink(
    modifier: Modifier = Modifier,
    isDialogShowing: Boolean,
    quantity: String,
    hour: Int,
    minute: Int,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onQuantityChange: (amount: String) -> Unit,
    onHourChange: (hour: Int) -> Unit,
    onMinuteChange: (minute: Int) -> Unit
) {
    if (isDialogShowing)
        Dialog(
            onDismissRequest = {
                onCancel()
            }
        ) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, end = 24.dp, start = 24.dp, top = 24.dp),
                    text = "Add Forgotten Drink",
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                    fontSize = 20.sp,
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, end = 24.dp, start = 24.dp),
                    value = quantity,
                    onValueChange = {
                        onQuantityChange(it)
                    },
                    label = {
                        Text(text = "quantity")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 24.dp, end = 24.dp, bottom = 20.dp)
                ) {
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
                        text = "CANCEL",
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