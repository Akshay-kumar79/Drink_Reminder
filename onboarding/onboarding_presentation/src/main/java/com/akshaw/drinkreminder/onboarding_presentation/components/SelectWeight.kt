package com.akshaw.drinkreminder.onboarding_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import com.akshaw.drinkreminder.onboarding_presentation.events.OnboardingEvent
import com.akshaw.drinkreminder.onboarding_presentation.OnboardingViewModel
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Suppress("MagicNumber")
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
                    minValue = Constants.MIN_WEIGHT
                    maxValue = Constants.MAX_WEIGHT
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 36f)
                    wheelItemCount = 3
                    value = viewModel.state.weight.roundToInt()
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { _, _, _ ->
                        viewModel.onEvent(OnboardingEvent.OnWeightChange(value))
                    }
                }
            }
        )
        AndroidView(
            modifier = Modifier.width(78.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    formatter = NumberPicker.Formatter {
                        WeightUnit.fromIdToName(it) ?: ""
                    }
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    maxValue = WeightUnit.maxID()
                    minValue = WeightUnit.minID()
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 32f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 24f)
                    wheelItemCount = 3
                    value = viewModel.state.weightUnit.id
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { _, _, _ ->
                        viewModel.onEvent(
                            OnboardingEvent.OnWeightUnitChange(
                                WeightUnit.fromId(value)
                            )
                        )
                    }
                }
            }
        )
    }
}