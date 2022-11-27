package com.akshaw.drinkreminder.feature_water.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.feature_water.presentation.report.components.ADayProgressCard
import com.akshaw.drinkreminder.feature_water.presentation.report.components.AverageReports
import com.akshaw.drinkreminder.feature_water.presentation.report.components.ChartSelector
import com.akshaw.drinkreminder.feature_water.presentation.report.components.WaterReportChart
import com.akshaw.drinkreminder.feature_water.utils.ChartType

@Composable
fun WaterReportScreen() {
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
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
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp, top = 20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.standing_person_image),
                contentDescription = null
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
        
        Spacer(modifier = Modifier.height(24.dp))
        ChartSelector(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            selectedChart = ChartType.WEEK,
            onSelect = {
            
            }
        )
    
        Spacer(modifier = Modifier.height(16.dp))
        WaterReportChart(
            chartType = ChartType.WEEK,
            data = listOf(23, 54, 34, 69, 100, 22, 10)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        AverageReports(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    
        Spacer(modifier = Modifier.height(16.dp))
    }
}