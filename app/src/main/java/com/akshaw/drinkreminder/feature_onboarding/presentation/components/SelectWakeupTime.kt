package com.akshaw.drinkreminder.feature_onboarding.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingEvent
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingViewModel
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Composable
fun SelectWakeupTime(viewModel: OnboardingViewModel, modifier: Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AndroidView(
            modifier = Modifier.width(78.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    formatter = NumberPicker.Formatter {
                        if (it in 0..9) {
                            "0$it"
                        } else {
                            "$it"
                        }
                    }
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    maxValue = 12
                    minValue = 1
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 36f)
                    wheelItemCount = 3
                    value = 6
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { picker, oldVal, newVal ->
                        viewModel.onEvent(OnboardingEvent.OnWakeupTimeHourChange(value.toString()))
                    }
                }
            }
        )
        AndroidView(
            modifier = Modifier.width(10.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    formatter = NumberPicker.Formatter { ":" }
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    maxValue = 100
                    minValue = 100
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 32f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 24f)
                    wheelItemCount = 3
                    value = 100
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                }
            }
        )
        AndroidView(
            modifier = Modifier.width(78.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    formatter = NumberPicker.Formatter {
                        if (it in 0..9) {
                            "0$it"
                        } else {
                            "$it"
                        }
                    }
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    maxValue = 59
                    minValue = 0
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 36f)
                    wheelItemCount = 3
                    value = 0
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { picker, oldVal, newVal ->
                        viewModel.onEvent(OnboardingEvent.OnWakeupTimeMinuteChange(value.toString()))
                    }
                }
            }
        )
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                NumberPicker(context).apply {
                    formatter = NumberPicker.Formatter {
                        if (it == 100)
                            TimeUnit.AM.name
                        else
                            TimeUnit.PM.name
                    }
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    maxValue = 101
                    minValue = 100
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 32f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 24f)
                    wheelItemCount = 3
                    value = 100
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { picker, oldVal, newVal ->
                        viewModel.onEvent(
                            OnboardingEvent.OnWakeupTimeUnitChange(
                                if (value == 100)
                                    TimeUnit.AM
                                else
                                    TimeUnit.PM
                            )
                        )
                    }
                }
            }
        )
    }
}