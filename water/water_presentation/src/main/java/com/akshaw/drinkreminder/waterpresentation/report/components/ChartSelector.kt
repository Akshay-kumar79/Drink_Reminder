package com.akshaw.drinkreminder.waterpresentation.report.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.utils.bounceClick
import com.akshaw.drinkreminder.waterdomain.utils.ChartType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartSelector(
    modifier: Modifier,
    selectedChart: ChartType,
    onSelect: (type: ChartType) -> Unit
) {
    
    Row(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .bounceClick()
                .height(32.dp)
                .width(84.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            colors = if (selectedChart == ChartType.WEEK)
                CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            else
                CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            onClick = {
                onSelect(ChartType.WEEK)
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "W",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    color = if (selectedChart == ChartType.WEEK)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.secondary
                )
            }
        }
        
        Spacer(modifier = Modifier.width(24.dp))
        
        Card(
            modifier = Modifier
                .bounceClick()
                .height(32.dp)
                .width(84.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            colors = if (selectedChart == ChartType.YEAR)
                CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
            else
                CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            onClick = {
                onSelect(ChartType.YEAR)
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Y",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    color = if (selectedChart == ChartType.YEAR)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
    
}