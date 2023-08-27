package com.akshaw.drinkreminder.settingspresentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R

// TODO remove if no plan to use in future

@Composable
fun SectionReminderSettings(
    modifier: Modifier,
    onNotificationSoundClick: () -> Unit,
    onRemindAlwaysClick: () -> Unit,
    onReminderModeClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = "REMINDER SETTINGS",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        // Notification preference
        NormalPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onNotificationSoundClick()
                },
            iconResId = R.drawable.volume_up_filled,
            text = "Notification sound",
        )
        
        // Remind always preference
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onRemindAlwaysClick()
                }
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.notification),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            
            Spacer(modifier = Modifier.width(24.dp))
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Remind always",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_regular,
                            FontWeight.Normal
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "remind you even if daily goal achieved",
                    fontSize = 10.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_regular,
                            FontWeight.Normal
                        )
                    ),
                    color = MaterialTheme.colorScheme.secondary
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
        
        // Reminder mode preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onReminderModeClick()
                },
            mainText = "Reminder mode",
            summaryText = "vibrate only",
            iconResId = R.drawable.phone
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}