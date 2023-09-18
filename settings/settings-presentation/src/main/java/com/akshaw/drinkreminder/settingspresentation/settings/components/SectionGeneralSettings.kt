package com.akshaw.drinkreminder.settingspresentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import kotlin.math.floor

@Composable
fun SectionGeneralSettings(
    modifier: Modifier,
    waterUnit: WaterUnit,
    weightUnit: WeightUnit,
    dailyIntakeGoal: Double,
    onUnitClick: () -> Unit,
    onDailyIntakeClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = "GENERAL SETTINGS",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        // Unit preference
        Spacer(modifier = Modifier.height(8.dp))
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onUnitClick()
                },
            mainText = "Unit",
            summaryText = "${waterUnit.text}/${weightUnit.name}",
            iconResId = R.drawable.meter
        )
        
        // Daily intake goal preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDailyIntakeClick()
                },
            mainText = "Daily intake goal",
            summaryText = "${floor(dailyIntakeGoal).toInt()} ${waterUnit.text}",
            iconResId = R.drawable.golf
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}