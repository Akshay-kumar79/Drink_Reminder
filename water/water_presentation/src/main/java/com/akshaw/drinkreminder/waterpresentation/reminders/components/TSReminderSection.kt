package com.akshaw.drinkreminder.waterpresentation.reminders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.akshaw.drinkreminder.core.R


@Composable
fun TSReminderSection(
    modifier: Modifier = Modifier
) {
    
    
    Column(modifier = modifier) {
        
        
        ReminderItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clickable {
                
                
                }
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
        ReminderItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clickable {
                
                
                }
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
        ReminderItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clickable {
                
                
                }
        )
        
        
        Spacer(modifier = Modifier.height(16.dp))
        FloatingActionButton(
            onClick = {
            
            
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )
        }
        
        
    }
    
    
    
    
}