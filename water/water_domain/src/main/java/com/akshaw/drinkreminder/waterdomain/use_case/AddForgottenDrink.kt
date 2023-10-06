package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 *  Add Forgotten drink in local database with the current waterUnit fetched from [Preferences]
 */
class AddForgottenDrink @Inject constructor(
    private val getLocalTime: GetLocalTime,
    private val addDrink: AddDrink,
    private val preferences: Preferences
) {
    /**
     *  @param hour hour at which drink to be registered
     *  @param minute minute at which drink to be registered
     *  @param date date on which drink to be registered
     *  @param quantity water amount to be registered
     *
     *  @return
     *  -> [Result.failure]
     *  - if failed to parse time with [hour] and [minute]
     *  - if [AddDrink.invoke] returns failure
     *
     *  -> [Result.success]
     *  if not failed then its surely success
     */
    suspend operator fun invoke(hour: Int, minute: Int, date: LocalDate, quantity: Double?): Result<Unit> {
        
        val localDateTime = getLocalTime(
            hour,
            minute
        ).getOrElse {
            return Result.failure(it)
        }.let {
            LocalDateTime.of(date, it)
        }
        
        addDrink(
            Drink(
                dateTime = localDateTime,
                waterIntake = quantity ?: 0.0,
                unit = preferences.getWaterUnit().first()
            )
        ).onFailure {
            return Result.failure(it)
        }
        
        return Result.success(Unit)
    }
    
}