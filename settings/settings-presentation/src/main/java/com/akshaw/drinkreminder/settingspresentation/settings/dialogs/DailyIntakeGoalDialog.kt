package com.akshaw.drinkreminder.settingspresentation.settings.dialogs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.corecompose.theme.composables.RadioGroup
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@Preview(showSystemUi = true, apiLevel = 33)
@Composable
private fun DailyIntakeGoalDialogPreview() {
    DrinkReminderTheme {
        DailyIntakeGoalDialog(
            dailyIntakeGoal = Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL,
            onDailyIntakeGoalChange = {},
            onReset = {},
            onCancel = {},
            onConfirm = {}
        )
    }
}

@Composable
fun DailyIntakeGoalDialog(
    modifier: Modifier = Modifier,
    dailyIntakeGoal: Double,
    onDailyIntakeGoalChange: (intake: Double) -> Unit,
    onReset: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    
    Dialog(
        onDismissRequest = { onCancel() }
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
                text = stringResource(id = R.string.daily_intake),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                fontSize = 20.sp,
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = floor(dailyIntakeGoal).toInt().toString(),
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                
                Icon(
                    modifier = Modifier
                        .clickable {
                            onReset()
                        },
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "refresh"
                )
                
            }
            
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                value = dailyIntakeGoal.toFloat(),
                valueRange = Constants.MIN_DAILY_WATER_INTAKE_GOAL.toFloat()..Constants.MAX_DAILY_WATER_INTAKE_GOAL.toFloat(),
                onValueChange = {
                    onDailyIntakeGoalChange(it.toDouble())
                },
                colors = SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant),
            )
            
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
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
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}