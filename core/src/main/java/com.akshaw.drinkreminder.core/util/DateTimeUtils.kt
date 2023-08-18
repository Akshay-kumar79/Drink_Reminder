package com.akshaw.drinkreminder.core.util

import java.time.LocalTime

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