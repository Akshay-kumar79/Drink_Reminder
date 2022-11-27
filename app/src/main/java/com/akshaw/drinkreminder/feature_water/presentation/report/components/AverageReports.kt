package com.akshaw.drinkreminder.feature_water.presentation.report.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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

@Composable
fun AverageReports(
    modifier: Modifier = Modifier
) {
    
    Column(modifier = modifier) {
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .15f))
                .padding(horizontal = 32.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    modifier = Modifier,
                    text = "Weekly average",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_medium,
                            FontWeight.Medium
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier,
                    text = "1,312 ml",
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
            
            Box {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp),
                    progress = 1f,
                    strokeWidth = 6.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = .6f)
                )
                
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 6.dp),
                    text = "92%",
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .15f))
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Average completion",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_medium,
                            FontWeight.Medium
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
        
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier,
                    text = "48%",
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
    
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Frequency",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.ubuntu_medium,
                            FontWeight.Medium
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
        
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier,
                    text = "1 time / day",
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
        }
        
    }
    
}