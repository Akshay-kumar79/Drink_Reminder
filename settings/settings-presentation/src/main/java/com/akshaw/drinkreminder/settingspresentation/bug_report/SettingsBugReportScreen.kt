package com.akshaw.drinkreminder.settingspresentation.bug_report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R

@Composable
fun SettingsBugReportScreen(
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
                text = "Bug report & Feedback",
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
            text = "System:",
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
            text = "System info(App v1.0.5, Model CPH1909, OS v8.1.0, Screen 720x1424, " +
                    "en _ US, GMT+05:30, engine com.google.android.tts /  " +
                    ", lang , select com.google.android.tts / Speech Services by Google):",
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
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .align(Alignment.CenterHorizontally),
            text = "please explain in brief so it helps us to find bug",
            fontSize = 12.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_medium,
                    FontWeight.Medium
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(212.dp)
                .padding(horizontal = 16.dp),
            value = "",
            onValueChange = {
            
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
            ),
            placeholder = {
                Text(
                    text = "enter you feedback...",
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_light,
                            FontWeight.Light
                        )
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp
                )
            }
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