package com.akshaw.drinkreminder.waterpresentation.report

import android.view.SoundEffectConstants
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.waterpresentation.report.components.ADayProgressCard
import com.akshaw.drinkreminder.waterpresentation.report.components.AverageReports
import com.akshaw.drinkreminder.waterpresentation.report.components.ChartSelector
import com.akshaw.drinkreminder.waterpresentation.report.components.WaterReportChart
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun WaterReportScreen(
    viewModel: WaterReportViewModel = hiltViewModel(),
    onADayDrinkClick: (date: LocalDate) -> Unit
) {
    
    val view = LocalView.current
    
    val goal by viewModel.goal.collectAsState()
    val currentWaterUnit by viewModel.waterUnit.collectAsState()
    val todayProgress by viewModel.todayProgress.collectAsState()
    val yesterdayProgress by viewModel.yesterdayProgress.collectAsState()
    val dayBeforeYesterdayProgress by viewModel.dayBeforeYesterdayProgress.collectAsState()
    
    // chart
    val selectedChart by viewModel.selectedChart.collectAsState()
    val chartSelectedWeeksFirstDay by viewModel.chartSelectedWeeksFirstDay.collectAsState()
    val chartSelectedYear by viewModel.chartSelectedYear.collectAsState()
    val chartData by viewModel.chartData.collectAsState()
    val isChartLeftAvailable by viewModel.isChartLeftAvailable.collectAsState()
    val isChartRightAvailable by viewModel.isChartRightAvailable.collectAsState()
    
    // weekly average
    val weeklyAverageProgress by viewModel.weeklyAverageProgress.collectAsState()
    val weeklyAverageCompletion by viewModel.weeklyAverageCompletion.collectAsState()
    val weeklyAverageFrequency by viewModel.weeklyAverageFrequency.collectAsState()
    
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
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onADayDrinkClick(LocalDate.now())
                    },
                    dayText = "Today",
                    progress = todayProgress,
                    goal = goal
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                ADayProgressCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 0.dp, end = 16.dp),
                    cardBackground = CardDefaults.cardColors(MaterialTheme.colorScheme.primary.copy(alpha = .15f)),
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onADayDrinkClick(LocalDate.now().minusDays(1))
                    },
                    dayText = "Yesterday",
                    progress = yesterdayProgress,
                    goal = goal
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
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onADayDrinkClick(LocalDate.now().minusDays(2))
                    },
                    dayText = LocalDate.now().minusDays(2).format(
                        DateTimeFormatter.ofPattern("dd LLL, yyyy", Locale.ENGLISH)
                    ),
                    progress = dayBeforeYesterdayProgress,
                    goal = goal
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        ChartSelector(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            selectedChart = selectedChart,
            onSelect = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(WaterReportEvent.OnChartTypeChange(it))
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        WaterReportChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .15f)),
            selectedChart = selectedChart,
            onChartLeftClick = {
                viewModel.onEvent(WaterReportEvent.OnChartLeftClick)
            },
            onChartRightClick = {
                viewModel.onEvent(WaterReportEvent.OnChartRightClick)
            },
            chartSelectedWeeksFirstDay = chartSelectedWeeksFirstDay,
            chartSelectedYear = chartSelectedYear,
            chartData = chartData,
            isChartLeftAvailable = isChartLeftAvailable,
            isChartRightAvailable = isChartRightAvailable
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        AverageReports(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            goal = goal,
            currentWaterUnit = currentWaterUnit,
            weeklyAverageProgress = weeklyAverageProgress,
            weeklyAverageCompletion = weeklyAverageCompletion,
            weeklyAverageFrequency = weeklyAverageFrequency
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}