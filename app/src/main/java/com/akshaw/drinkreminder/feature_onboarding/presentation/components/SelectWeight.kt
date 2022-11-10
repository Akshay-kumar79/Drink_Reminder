package com.akshaw.drinkreminder.feature_onboarding.presentation.components

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingEvent
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingViewModel
import com.akshaw.drinkreminder.ui.theme.DrinkReminderTheme
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Composable
fun SelectWeight(viewModel: OnboardingViewModel, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AndroidView(
            modifier = Modifier.width(78.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    maxValue = 200
                    minValue = 20
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 36f)
                    wheelItemCount = 3
                    value = 75
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { picker, oldVal, newVal ->
                        viewModel.onEvent(OnboardingEvent.OnWeightChange(value))
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
                            WeightUnit.KG.name
                        else
                            WeightUnit.LBS.name
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
                            OnboardingEvent.OnWeightUnitChange(
                                if (value == 100)
                                    WeightUnit.KG
                                else
                                    WeightUnit.LBS
                            )
                        )
                    }
                }
            }
        )
    }
}