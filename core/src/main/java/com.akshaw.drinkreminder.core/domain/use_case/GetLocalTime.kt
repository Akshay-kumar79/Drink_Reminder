package com.akshaw.drinkreminder.core.domain.use_case

import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.util.truncateToMinutes
import java.time.LocalTime

/**
 *  Provided [LocalTime] object
 */
class GetLocalTime {
    
    /**
     *  @param hour hour according to 12 hour clock range(1..12)
     *  @param minute minute of an hour
     *  @param unit [TimeUnit]
     *
     *  @return
     *  -> [Result.failure] if fails to parse hour or minute
     *
     *  -> [Result.success] with [LocalTime]
     */
    operator fun invoke(hour: Int, minute: Int, unit: TimeUnit): Result<LocalTime> {
        
        val clock24Hour = if (hour >= 13 || hour <= 0){
            return Result.failure(Exception(""))
        }else{
            when(unit){
                TimeUnit.AM -> {
                    if (hour == 12) 0 else hour
                }
                TimeUnit.PM -> {
                    if (hour == 12) 12 else hour + 12
                }
            }
        }
        
        return invoke(clock24Hour, minute)
    }
    
    /**
     *  @param clock24Hour hour according to 24 hour clock range(0..23)
     *  @param minute minute of an hour
     *
     *  @return
     *  -> [Result.failure] if fails to parse hour or minute
     *
     *  -> [Result.success] with [LocalTime]
     */
    operator fun invoke(clock24Hour: Int, minute: Int): Result<LocalTime> {
        
        return try {
            Result.success(
                LocalTime.now()
                    .withHour(clock24Hour)
                    .withMinute(minute)
                    .truncateToMinutes()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
}