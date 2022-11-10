package com.akshaw.drinkreminder.feature_onboarding.presentation.components

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingEvent
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingViewModel
import com.akshaw.drinkreminder.ui.theme.DrinkReminderTheme
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Preview
@Composable
private fun Preview(){
    DrinkReminderTheme {
        val possibleValues = listOf("kg", "lbs")
        var state by remember { mutableStateOf(possibleValues[0]) }
        ListItemPicker(
            value = state,
            onValueChange = { state = it },
            list = possibleValues,
            textStyle = TextStyle(
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_medium, FontWeight.Medium)),
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}


@Composable
fun SelectWeight(viewModel: OnboardingViewModel, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AndroidView(
            modifier = Modifier,
            factory = { context ->
                NumberPicker(context).apply {
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = Color.parseColor("#A3B4C4")
                    fadingEdgeStrength = 1f
                    maxValue = 999
                    minValue = 20
                    selectedTextColor = Color.parseColor("#152F48")
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = Color.parseColor("#A3B4C4")
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
                    dividerColor = Color.parseColor("#A3B4C4")
                    fadingEdgeStrength = 1f
                    maxValue = 100
                    minValue = 101
                    selectedTextColor = Color.parseColor("#152F48")
                    selectedTextSize = Utility.getFloatFromSp(context, 32f)
                    textColor = Color.parseColor("#A3B4C4")
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