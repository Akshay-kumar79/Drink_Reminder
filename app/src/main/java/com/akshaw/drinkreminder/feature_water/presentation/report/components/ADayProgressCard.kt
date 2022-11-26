package com.akshaw.drinkreminder.feature_water.presentation.report.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.R
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ADayProgressCard(
    modifier: Modifier,
    cardBackground: CardColors,
    onClick: () -> Unit,
    dayText: String,
    progress: Double,
    goal: Double
) {
    Card(
        modifier = modifier,
        colors = cardBackground,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onClick()
        }
    ) {
        
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = dayText,
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(45.dp)),
                progress = (progress / goal).toFloat(),
                trackColor = MaterialTheme.colorScheme.background
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 22.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${ceil(progress).toInt()} / ${ceil(goal).toInt()}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Text(
                    text = "${(progress*100/goal).let {
                        if (it <= 100) ceil(it).toInt() else 100
                    }}%",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_bold,
                            FontWeight.Bold
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}