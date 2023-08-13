package com.akshaw.drinkreminder.waterpresentation.reminders.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R

@Composable
fun ReminderItem(
    modifier: Modifier = Modifier
) {
    
    Row(
        modifier = modifier
    ) {
        
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = "09:20",
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Text(
                text = "Sun, Mon, Tue, Wed, Thu, Fri",
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_regular,
                        FontWeight.Normal
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        
        Switch(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .scale(.65f),
            checked = true,
            onCheckedChange = {
            
            }
        )
        Spacer(modifier = Modifier.width(6.dp))
        
    }
    
}