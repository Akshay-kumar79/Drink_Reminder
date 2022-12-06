package com.akshaw.drinkreminder.feature_water.presentation.report.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun WaterReportChart(
    modifier: Modifier = Modifier,
    selectedChart: ChartType,
    chartSelectedWeeksFirstDay: LocalDate,
    chartSelectedYear: Year,
    data: List<Int>,
    onChartLeftClick: () -> Unit,
    onChartRightClick: () -> Unit
) {
    
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier
                .height(24.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 16.dp),
                onClick = {
                    onChartLeftClick()
                },
                enabled = false
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.left_arrow_icon),
                    contentDescription = null
                )
            }
            
            
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = when(selectedChart){                         // "20 Nov - 26 Nov"  Or  "2022"
                    ChartType.WEEK -> {
                        val formatter = DateTimeFormatter.ofPattern("dd LLL", Locale.ENGLISH)
                        val firstDay = chartSelectedWeeksFirstDay.format(formatter)
                        val lastDay = chartSelectedWeeksFirstDay.plusWeeks(1).minusDays(1).format(formatter)
                        "$firstDay - $lastDay"
                    }
                    ChartType.YEAR -> chartSelectedYear.value.toString()
                },
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
    
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 16.dp),
                onClick = {
                    onChartRightClick()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                    contentDescription = null
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(216.dp)
                .padding(horizontal = 16.dp)
        ) {
            data.forEachIndexed { index, it ->
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((2 * it).dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .6f))
                        ) {
                        
                        }
                        
                        Text(
                            modifier = Modifier
                                .height(16.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "Su",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.ubuntu_medium,
                                    FontWeight.Medium
                                )
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 4.dp)
                            .offset(y = if (it > 10) (0).dp else (-20).dp),
                        text = it.toString(),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.ubuntu_regular,
                                FontWeight.Normal
                            )
                        ),
                        color = if (it > 10)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onBackground
                    )
                }
                
                
                if (data.lastIndex != index) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
    
    
}