package com.akshaw.drinkreminder.coretest

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FakePreference : Preferences {
    
    private var gender: String = Constants.DEFAULT_GENDER.name
    private var age: Int = Constants.DEFAULT_AGE
    private var weight: Float = Constants.DEFAULT_WEIGHT.toFloat()
    private var weightUnit: String = Constants.DEFAULT_WEIGHT_UNIT.name
    private var waterUnit: String = Constants.DEFAULT_WATER_UNIT.name
    private var selectedTrackableDrinkId: Long = Constants.DEFAULT_SELECTED_TRACKABLE_DRINK_ID
    private var dailyWaterIntakeGoal: Double = Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL
    private var isOnboardingCompleted: Boolean = Constants.DEFAULT_IS_ONBOARDING_COMPLETED
    
    //TODO change time to sync from Constants files
    private var bedTime: String = "22:00"
    private var wakeupTime: String = "07:00"
    
    
    override suspend fun saveGender(gender: Gender) {
        this.gender = gender.name
    }
    
    override suspend fun saveAge(age: Int) {
        this.age = age
    }
    
    override suspend fun saveWeight(weight: Float) {
        this.weight = weight
    }
    
    override suspend fun saveWeightUnit(unit: WeightUnit) {
        this.weightUnit = unit.name
    }
    
    override suspend fun saveBedTime(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        val timeString = time.format(formatter)
        
        this.bedTime = timeString
    }
    
    override suspend fun saveWakeupTime(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        val timeString = time.format(formatter)
        
        this.wakeupTime = timeString
    }
    
    override suspend fun saveWaterUnit(unit: WaterUnit) {
        this.waterUnit = unit.name
    }
    
    override suspend fun saveSelectedTrackableDrinkId(id: Long) {
        this.selectedTrackableDrinkId = id
    }
    
    override suspend fun saveDailyWaterIntakeGoal(amount: Double) {
        this.dailyWaterIntakeGoal = amount
    }
    
    override suspend fun saveIsOnboardingCompleted(completed: Boolean) {
        this.isOnboardingCompleted = completed
    }
    
    
    override fun getGender(): Flow<Gender> {
        return flow {
            emit(Gender.fromString(gender))
        }
    }
    
    override fun getAge(): Flow<Int> {
        return flow {
            emit(age)
        }
    }
    
    override fun getWeight(): Flow<Float> {
        return flow {
            emit(weight)
        }
    }
    
    override fun getWeightUnit(): Flow<WeightUnit> {
        return flow {
            emit(WeightUnit.fromString(weightUnit))
        }
    }
    
    override fun getBedTime(): Flow<LocalTime> {
        return flow {
            emit(LocalTime.parse(bedTime))
        }
    }
    
    override fun getWakeupTime(): Flow<LocalTime> {
        return flow {
            emit(LocalTime.parse(wakeupTime))
        }
    }
    
    override fun getWaterUnit(): Flow<WaterUnit> {
        return flow {
            emit(WaterUnit.fromString(waterUnit))
        }
    }
    
    override fun getSelectedTrackableDrinkId(): Flow<Long> {
        return flow {
            emit(selectedTrackableDrinkId)
        }
    }
    
    override fun getDailyWaterIntakeGoal(): Flow<Double> {
        return flow {
            emit(dailyWaterIntakeGoal)
        }
    }
    
    override fun getIsOnboardingCompleted(): Flow<Boolean> {
        return flow {
            emit(isOnboardingCompleted)
        }
    }
}