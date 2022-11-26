package com.akshaw.drinkreminder.feature_water.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.feature_water.presentation.report.components.ADayProgressCard

@Composable
fun WaterReportScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            text = "Report",
            fontSize = 20.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(top = 32.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 24.dp, top = 20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.standing_person_image),
                contentDescription = null
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                
                ADayProgressCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 0.dp, end = 16.dp)
                        .offset(x = (-20).dp),
                    cardBackground = CardDefaults.cardColors(MaterialTheme.colorScheme.primary.copy(alpha = .15f)),
                    onClick = {
                    
                    },
                    dayText = "Today",
                    progress = 938.0,
                    goal = 2350.0
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                ADayProgressCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 0.dp, end = 16.dp),
                    cardBackground = CardDefaults.cardColors(MaterialTheme.colorScheme.primary.copy(alpha = .15f)),
                    onClick = {
                    
                    },
                    dayText = "Yesterday",
                    progress = 1508.0,
                    goal = 2350.0
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                ADayProgressCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 0.dp, end = 16.dp)
                        .offset(x = (-10).dp),
                    cardBackground = CardDefaults.cardColors(MaterialTheme.colorScheme.primary.copy(alpha = .15f)),
                    onClick = {
            
                    },
                    dayText = "12 Nov, 2022",
                    progress = 308.0,
                    goal = 2350.0
                )
            }
        }
    }
}