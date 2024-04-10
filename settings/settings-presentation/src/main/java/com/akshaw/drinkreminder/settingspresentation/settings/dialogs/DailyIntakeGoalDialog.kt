package com.akshaw.drinkreminder.settingspresentation.settings.dialogs

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import kotlin.math.floor

@Preview(showSystemUi = true, apiLevel = 33)
@Composable
private fun DailyIntakeGoalDialogPreview() {
    DrinkReminderTheme {
        DailyIntakeGoalDialog(
            dailyIntakeGoal = Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL,
            onDailyIntakeGoalChange = {},
            onReset = {},
            onCancel = {},
            onConfirm = {},
            currentWaterUnit = WaterUnit.ML
        )
    }
}

@Composable
fun DailyIntakeGoalDialog(
    modifier: Modifier = Modifier,
    dailyIntakeGoal: Double,
    currentWaterUnit: WaterUnit,
    onDailyIntakeGoalChange: (intake: Double) -> Unit,
    onReset: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    val view = LocalView.current
    
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
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onReset()
                        },
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "reset"
                )
                
            }
            
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                value = dailyIntakeGoal.toFloat(),
                valueRange = when (currentWaterUnit) {
                    WaterUnit.ML ->
                        Constants.MIN_DAILY_WATER_INTAKE_GOAL_ML.toFloat()..Constants.MAX_DAILY_WATER_INTAKE_GOAL_ML.toFloat()
                    
                    WaterUnit.FL_OZ ->
                        Constants.MIN_DAILY_WATER_INTAKE_GOAL_FL_OZ.toFloat()..Constants.MAX_DAILY_WATER_INTAKE_GOAL_FL_OZ.toFloat()
                },
                onValueChange = {
                    onDailyIntakeGoalChange(it.toDouble())
                },
                colors = SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)),
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
                            view.playSoundEffect(SoundEffectConstants.CLICK)
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
                            view.playSoundEffect(SoundEffectConstants.CLICK)
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