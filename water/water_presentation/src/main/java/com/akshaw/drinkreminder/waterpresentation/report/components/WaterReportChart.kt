package com.akshaw.drinkreminder.waterpresentation.report.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.waterdomain.utils.ChartType
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("MagicNumber")
@Composable
fun WaterReportChart(
    modifier: Modifier = Modifier,
    selectedChart: ChartType,
    chartSelectedWeeksFirstDay: LocalDate,
    chartSelectedYear: Year,
    onChartLeftClick: () -> Unit,
    onChartRightClick: () -> Unit,
    chartData: List<Int>,
    isChartLeftAvailable: Boolean,
    isChartRightAvailable: Boolean
) {
    
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Row(
            modifier = Modifier
                .height(36.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            IconButton(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(36.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    onChartLeftClick()
                },
                enabled = isChartLeftAvailable
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.left_arrow_icon),
                    contentDescription = null
                )
            }
            
            
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = when (selectedChart) {                         // "20 Nov - 26 Nov"  Or  "2022"
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
                    .padding(start = 10.dp)
                    .size(36.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    onChartRightClick()
                },
                enabled = isChartRightAvailable
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                    contentDescription = null
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(216.dp)
                .padding(horizontal = 16.dp)
        ) {
            val days = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
            val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
            
            chartData.forEachIndexed { index, data ->
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((2 * data).dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .6f))
                        ) {
                        
                        }
                        
                        Text(
                            modifier = Modifier
                                .height(16.dp)
                                .align(Alignment.CenterHorizontally)
                                .rotate(when (selectedChart) {
                                    ChartType.WEEK -> 0f
                                    ChartType.YEAR -> -20f
                                }),
                            text = when (selectedChart) {
                                ChartType.WEEK -> {
                                    try {
                                        days[index]
                                    } catch (e: Exception) {
                                        ""
                                    }
                                }
                                
                                ChartType.YEAR -> {
                                    try {
                                        months[index]
                                    } catch (e: Exception) {
                                        ""
                                    }
                                }
                            },
                            fontSize = when (selectedChart) {
                                ChartType.WEEK -> 16.sp
                                ChartType.YEAR -> 10.sp
                            },
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
                            .offset(y = if (data > 10) (0).dp else (-20).dp),
                        text = data.toString(),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.ubuntu_regular,
                                FontWeight.Normal
                            )
                        ),
                        color = if (data > 10)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onBackground
                    )
                }
                
                
                if (chartData.lastIndex != index) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
    
    
}