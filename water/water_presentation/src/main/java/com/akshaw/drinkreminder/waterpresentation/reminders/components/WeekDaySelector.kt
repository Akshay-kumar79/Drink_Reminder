package com.akshaw.drinkreminder.waterpresentation.reminders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import java.time.DayOfWeek

@Preview
@Composable
fun WeekDaySelectorPreview() {
    DrinkReminderTheme {
        WeekDaySelector(
            modifier = Modifier
                .fillMaxWidth(),
            selectedDays = DayOfWeek.values().associateBy(
                { it.value },
                { it !in setOf(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY) }
            ),
            onDaySelectionChange = { }
        )
    }
}

/**
 *  @param selectedDays map with key as DayOfWeek.value and value as true of false (if day is selected or not).
 */
@Composable
fun WeekDaySelector(
    modifier: Modifier = Modifier,
    selectedDays: Map<Int, Boolean>,
    onDaySelectionChange: (dayOfWeek: DayOfWeek) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DayOfWeek.values().forEach {
            DayOfWeekComp(
                selected = selectedDays.getOrDefault(it.value, false),
                dayOfWeek = it,
                onDaySelectionChange = onDaySelectionChange
            )
        }
    }
}

@Composable
private fun DayOfWeekComp(
    selected: Boolean,
    dayOfWeek: DayOfWeek,
    onDaySelectionChange: (dayOfWeek: DayOfWeek) -> Unit
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(
                color = if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.inverseSurface
            ).clickable {
                onDaySelectionChange(dayOfWeek)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayOfWeek.name.take(3),
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
            fontSize = 12.sp,
            color = if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
            MaterialTheme.colorScheme.inverseOnSurface
        )
    }
}