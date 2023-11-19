package com.akshaw.drinkreminder.core.util

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Suppress("MagicNumber")
fun LocalTime.formatted24HourTime(): String {
    return "${
        if (hour <= 9)
            "0$hour"
        else
            hour
    }:" + "${
        if (minute <= 9)
            "0$minute"
        else
            minute
    }"
}

/**
 *  @return string containing all days with first 3 letter in the list separated by a comma and space(", ")
 */
@Suppress("MagicNumber")
fun List<DayOfWeek>.formattedString(): String {
    return buildString {
        this@formattedString.forEachIndexed { index, dayOfWeek ->
            val threeLetterDay = dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
            append(threeLetterDay)
            if (index != this@formattedString.lastIndex)
                append(", ")
        }
    }
}

fun LocalTime.truncateToMinutes(): LocalTime = this.truncatedTo(ChronoUnit.MINUTES)
