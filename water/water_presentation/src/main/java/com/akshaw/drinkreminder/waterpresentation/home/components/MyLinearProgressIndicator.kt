package com.akshaw.drinkreminder.waterpresentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun MyLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Double,
    goal: Double,
    round: Dp,
    waterUnit: WaterUnit
) {
    
    val roundInPx = with(LocalDensity.current) {
        round.toPx()
    }
    
    val trackColor = MaterialTheme.colorScheme.primary.copy(0.65f)
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onBackground
    
    val textMeasurer = rememberTextMeasurer()
    
    Canvas(modifier = modifier) {
        
        val newProgress by derivedStateOf {
            progress.coerceIn(0.0, goal)
        }
        
        val progressValue by derivedStateOf {
            size.width * (newProgress / goal).toFloat()
        }
        
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(roundInPx, roundInPx)
        )
        drawRoundRect(
            color = trackColor,
            size = Size(progressValue, size.height),
            cornerRadius = CornerRadius(roundInPx, roundInPx)
        )
        
        
        val progressText = "${progress.roundToInt()} ${waterUnit.text}"
        val progressTextStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = textColor,
        )
        val progressTextLayoutResult = textMeasurer.measure(progressText, progressTextStyle)
        drawText(
            textMeasurer = textMeasurer,
            text = progressText,
            topLeft = Offset(
                0f + 8.dp.toPx(),
                size.height - progressTextLayoutResult.size.height - 6.dp.toPx()
            ),
            style = progressTextStyle
        )
        
        
        val goalText = "${floor(goal).toInt()} ${waterUnit.text}"
        val goalTextStyle = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = textColor,
        )
        val goalTextLayoutResult = textMeasurer.measure(goalText, goalTextStyle)
        drawText(
            textMeasurer = textMeasurer,
            text = goalText,
            topLeft = Offset(
                size.width - goalTextLayoutResult.size.width - 8.dp.toPx(),
                size.height - goalTextLayoutResult.size.height - 6.dp.toPx()
            ),
            style = goalTextStyle
        )
    }
    
}