package com.akshaw.drinkreminder.settingspresentation.faq

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R

// TODO add faq to check notification and alarm permission if alarm not working
@Composable
fun SettingsFaqScreen(
    onBackClicked: () -> Unit
) {
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier
                    .padding(start = 4.dp),
                onClick = {
                    onBackClicked()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.left_arrow_icon),
                    contentDescription = null
                )
            }
            
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "FAQ",
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_bold,
                        FontWeight.Bold
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.q_why_doesn_t_the_reminder_work),
            fontSize = 12.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_medium,
                    FontWeight.Medium
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = "A: There are several reasons. You may check settings as follows:\n" +
                    "1. Is your bed time and wake-up time set appropriately?\n" +
                    "2. Have you enabled Drink Water Reminder to auto start on your phone?\n" +
                    "3. Have you installed third-party apps like \"Clean Master\" which may prohibit Drink Water Reminder running in background and send notifications?\n" +
                    "4. Have you turned off the reminder?\n" +
                    "5. may be your battery optimization is on for this app. please turn off battery optimization for \"Drink Water Reminder\".",
            fontSize = 12.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_light,
                    FontWeight.Light
                )
            ),
            color = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 84.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp),
            onClick = {
            
            }) {
            Text(
                text = "Go to Settings",
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = "Q: Why does the Reminder have no sound?",
            fontSize = 12.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_medium,
                    FontWeight.Medium
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = "A: Please check settings as follows:\n" +
                    "1. Have you mute your phone?\n" +
                    "2. Have you turned off the Reminder sound or set the Reminder volume to 0 in the Reminder option of Water Drink Reminder?",
            fontSize = 12.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_light,
                    FontWeight.Light
                )
            ),
            color = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Note:",
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "if you still getting problem than your can report us.",
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_light,
                        FontWeight.Light
                    )
                ),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 84.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp),
            onClick = {
            
            }) {
            Text(
                text = "Report Bug",
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp
            )
        }
        
    }
    
}