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
fun SectionCriticalSettings(
    modifier: Modifier,
    onResetDataClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = "CRITICAL",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        // Reset data preference
        Spacer(modifier = Modifier.height(8.dp))
        NormalPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onResetDataClick()
                },
            text = "Reset data",
            iconResId = R.drawable.bx_reset
        )
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}