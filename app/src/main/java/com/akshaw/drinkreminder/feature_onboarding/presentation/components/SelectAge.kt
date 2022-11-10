package com.akshaw.drinkreminder.feature_onboarding.presentation.components

import android.graphics.Color
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
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingEvent
import com.akshaw.drinkreminder.feature_onboarding.presentation.OnboardingViewModel
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Composable
fun SelectAge(viewModel: OnboardingViewModel, modifier: Modifier) {
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
                    maxValue = 150
                    minValue = 10
                    selectedTextColor = Color.parseColor("#152F48")
                    selectedTextSize = Utility.getFloatFromSp(context, 48f)
                    textColor = Color.parseColor("#A3B4C4")
                    textSize = Utility.getFloatFromSp(context, 36f)
                    wheelItemCount = 3
                    value = 20
                    typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                    setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                    setOnValueChangedListener { picker, oldVal, newVal ->
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
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_medium, FontWeight.Medium))
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }

}

