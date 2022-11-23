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

    operator fun invoke(clock24Hour: Int, clock24minute: Int): Result<LocalTime> {

        val hourString = if (clock24Hour in 0..9) "0$clock24Hour" else clock24Hour.toString()
        val minuteString = if (clock24minute in 0..9) "0$clock24minute" else clock24minute.toString()
        val timeString24Hour = "$hourString:$minuteString"

        return try {
            Result.success(
                LocalTime.parse(
                    timeString24Hour,
                    DateTimeFormatter.ofPattern("H:mm", Locale.ENGLISH)
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}