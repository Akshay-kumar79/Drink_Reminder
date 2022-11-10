package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.TimeUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GetLocalTime {

    operator fun invoke(hour: String, minute: String, unit: TimeUnit): Result<LocalTime> {
        val h = hour.toIntOrNull()
        val m = minute.toIntOrNull()

        if (h == null || m == null) {
            return Result.failure(NullPointerException())
        }

        val hourString = if (h in 0..9) "0$h" else h.toString()
        val minuteString = if (m in 0..9) "0$m" else m.toString()
        val timeString12Hour = "$hourString:$minuteString ${unit.name}"

        return try {
            Result.success(
                LocalTime.parse(
                    timeString12Hour,
                    DateTimeFormatter.ofPattern("h:mm a")
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}