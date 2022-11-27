package com.akshaw.drinkreminder.feature_water.presentation.report.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.feature_water.utils.ChartType

@Composable
fun WaterReportChart(
    modifier: Modifier = Modifier,
    chartType: ChartType,
    data: List<Int>
) {
    
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .15f))
    ) {
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "20 Nov - 26 Nov",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_medium,
                    FontWeight.Medium
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
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