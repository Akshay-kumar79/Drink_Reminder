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

@Composable
fun SectionPersonalInformation(
    modifier: Modifier,
    onGenderClick: () -> Unit,
    onAgeClick: () -> Unit,
    onWeightClick: () -> Unit,
    onSleepTimeClick: () -> Unit,
    onWakeUpTimeClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = "PERSONAL INFORMATION",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        // Gender preference
        Spacer(modifier = Modifier.height(8.dp))
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onGenderClick()
                },
            mainText = "Gender",
            summaryText = "male",
            iconResId = R.drawable.gender
        )
        
        // Age preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onAgeClick()
                },
            mainText = "Age",
            summaryText = "20 years",
            iconResId = R.drawable.age_icon
        )
        
        // Weight preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onWeightClick()
                },
            mainText = "Weight",
            summaryText = "70 kg",
            iconResId = R.drawable.icon_awesome_weight
        )
        
        // Sleep time preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSleepTimeClick()
                },
            mainText = "Bed time",
            summaryText = "22:00",
            iconResId = R.drawable.sleep
        )
        
        // Wake-up time preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onWakeUpTimeClick()
                },
            mainText = "Wake-up time",
            summaryText = "07:00",
            iconResId = R.drawable.sun
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}