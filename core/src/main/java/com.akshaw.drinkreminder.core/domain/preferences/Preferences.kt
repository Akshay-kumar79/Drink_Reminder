package com.akshaw.drinkreminder.core.domain.preferences

import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


interface Preferences {
    
    suspend fun saveGender(gender: Gender)
    suspend fun saveAge(age: Int)
    suspend fun saveWeight(weight: Float)
    suspend fun saveWeightUnit(unit: WeightUnit)
    suspend fun saveBedTime(time: LocalTime)
    suspend fun saveWakeupTime(time: LocalTime)
    suspend fun saveWaterUnit(unit: WaterUnit)
    suspend fun saveSelectedTrackableDrinkId(id: Long)
    suspend fun saveDailyWaterIntakeGoal(amount: Double)
    suspend fun saveIsOnboardingCompleted(completed: Boolean)
    
    fun getGender(): Flow<Gender>
    fun getAge(): Flow<Int>
    fun getWeight(): Flow<Float>
    fun getWeightUnit(): Flow<WeightUnit>
    fun getBedTime(): Flow<LocalTime>
    fun getWakeupTime(): Flow<LocalTime>
    fun getWaterUnit(): Flow<WaterUnit>
    fun getSelectedTrackableDrinkId(): Flow<Long>
    fun getDailyWaterIntakeGoal(): Flow<Double>
    fun getIsOnboardingCompleted(): Flow<Boolean>

    companion object{

        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_WEIGHT = "weight"
        const val KEY_WEIGHT_UNIT = "weight_unit"
        const val KEY_BED_TIME = "bed_time"
        const val KEY_WAKEUP_TIME = "wakeup_time"
        const val KEY_WATER_UNIT = "water_unit"
        const val KEY_SELECTED_TRACKABLE_DRINK_ID = "selected_trackable_drink_id"
        const val KEY_DAILY_WATER_INTAKE_GOAL = "daily_water_intake_goal"
        const val KEY_IS_ONBOARDING_COMPLETED = "is_onboarding_completed"
        
    }

}