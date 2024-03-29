package com.akshaw.drinkreminder.waterpresentation.reminders.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.model.DrinkReminder
import com.akshaw.drinkreminder.corecompose.utils.bounceClick


/**
 * Time Specific Reminder Section
 */
@Composable
fun TSReminderSection(
    modifier: Modifier = Modifier,
    onAddNewReminderClick: () -> Unit,
    allDrinkReminders: List<DrinkReminder>,
    onReminderClick: (drinkReminder: DrinkReminder) -> Unit,
    onReminderSwitchChange: (drinkReminder: DrinkReminder, isReminderOn: Boolean) -> Unit,
    onDeleteReminder: (drinkReminder: DrinkReminder) -> Unit
) {
    val view = LocalView.current
    
    Column(modifier = modifier) {
        
        allDrinkReminders.forEach {
            ReminderItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                drinkReminder = it,
                onClick = { drinkReminder ->
                    onReminderClick(drinkReminder)
                },
                onSwitchCheckChange = { drinkReminder, isReminderOn ->
                    onReminderSwitchChange(drinkReminder, isReminderOn)
                },
                onDeleteReminder = onDeleteReminder
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        FloatingActionButton(
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onAddNewReminderClick()
            },
            modifier = Modifier
                .bounceClick()
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
        Spacer(modifier = Modifier.height(32.dp))
        
    }
}