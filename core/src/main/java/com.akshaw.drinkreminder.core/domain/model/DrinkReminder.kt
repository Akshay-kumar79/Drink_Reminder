package com.akshaw.drinkreminder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalTime

@Parcelize
data class DrinkReminder(
    
    val id: Long = 0,
    var time: LocalTime,
    var isReminderOn: Boolean = true,
    var activeDays: List<DayOfWeek>
    
) : Parcelable