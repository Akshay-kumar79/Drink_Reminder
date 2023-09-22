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
     *  @return [Result.failure] if failed to parse time with [hour] and [minute]
     *  or if program reached the end of invoke which should not happen
     */
    suspend operator fun invoke(hour: Int, minute: Int, date: LocalDate, quantity: Double?): Result<Unit> {
        getLocalTime(
            hour,
            minute
        )
            .onSuccess { localTime ->
                addDrink(
                    Drink(
                        dateTime = LocalDateTime.of(
                            date,
                            localTime
                        ),
                        waterIntake = quantity ?: 0.0,
                        unit = preferences.getWaterUnit().first()
                    )
                ).onSuccess {
                    return Result.success(Unit)
                }.onFailure {
                    return Result.failure(it)
                }
            }
            .onFailure {
                return Result.failure(it)
            }
        
        return Result.failure(Exception("Something went wrong"))
    }
    
}