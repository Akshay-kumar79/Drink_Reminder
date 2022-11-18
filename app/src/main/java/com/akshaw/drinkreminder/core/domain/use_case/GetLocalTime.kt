package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.TimeUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class GetLocalTime {

    operator fun invoke(hour: Int, minute: Int, unit: TimeUnit): Result<LocalTime> {

        val hourString = if (hour in 0..9) "0$hour" else hour.toString()
        val minuteString = if (minute in 0..9) "0$minute" else minute.toString()
        val timeString12Hour = "$hourString:$minuteString ${unit.name}"

        return try {
            Result.success(
                LocalTime.parse(
                    timeString12Hour,
                    DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}