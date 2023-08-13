package com.akshaw.drinkreminder.waterpresentation.reminders.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R


@Composable
fun AIReminderSection(
    modifier: Modifier = Modifier
) {
    
    
    Column(modifier = modifier) {
        
        
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "1 hour 20 minute",
            fontSize = 24.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_medium,
                    FontWeight.Medium
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Time Interval",
            fontSize = 12.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.roboto_regular,
                    FontWeight.Normal
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        
        Spacer(modifier = Modifier.height(24.dp))
        
        
    }
    
    
}