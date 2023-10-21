package com.akshaw.drinkreminder.onboarding_presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.onboarding_presentation.events.OnboardingEvent
import com.akshaw.drinkreminder.onboarding_presentation.OnboardingViewModel
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt
import com.akshaw.drinkreminder.core.R


@Composable
fun SelectAge(viewModel: OnboardingViewModel, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AndroidView(
            modifier = Modifier.width(100.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                    setDividerThickness(Utility.getFloatFromDp(context, 0.5f).roundToInt())
                    dividerColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    fadingEdgeStrength = 1f
                    minValue = Constants.MIN_AGE
                    maxValue = Constants.MAX_AGE
                    selectedTextColor = context.getColor(R.color.on_background)
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                    textSize = Utility.getFloatFromSp(context, 36f)
                    wheelItemCount = 3
                    value = viewModel.state.age
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { _, _, _ ->
                        viewModel.onEvent(
                            OnboardingEvent.OnAgeChange(value)
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.years),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_medium, FontWeight.Medium))
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }

}

