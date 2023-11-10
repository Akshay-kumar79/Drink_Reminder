package com.akshaw.drinkreminder.core.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.akshaw.drinkreminder.core.domain.preferences.Persistent
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore("default_pref")

class DefaultPreference(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) : Preferences {
    
    companion object{
        const val MIN_TWO_DIGIT_NUMBER = 10
    }
    
    private val genderKey = stringPreferencesKey(Preferences.KEY_GENDER)
    private val ageKey = intPreferencesKey(Preferences.KEY_AGE)
    private val weightKey = floatPreferencesKey(Preferences.KEY_WEIGHT)
    private val weightUnitKey = stringPreferencesKey(Preferences.KEY_WEIGHT_UNIT)
    private val bedTimeKey = stringPreferencesKey(Preferences.KEY_BED_TIME)
    private val wakeTimeKey = stringPreferencesKey(Preferences.KEY_WAKEUP_TIME)
    private val waterUnitKey = stringPreferencesKey(Preferences.KEY_WATER_UNIT)
    private val selectedTrackableDrinkIdKey = longPreferencesKey(Preferences.KEY_SELECTED_TRACKABLE_DRINK_ID)
    private val dailyWaterIntakeGoalKey = doublePreferencesKey(Preferences.KEY_DAILY_WATER_INTAKE_GOAL)
    private val isOnboardingCompletedKey = booleanPreferencesKey(Preferences.KEY_IS_ONBOARDING_COMPLETED)
    
    /**
     *  Value are stored as string in preference extracted from [Persistent.genderValues]
     *  for there respective [Gender]
     */
    override suspend fun saveGender(gender: Gender) {
        dataStore.edit { preference ->
            // Here getValue should not throw NoSuchElementException as genderValues have
            // all the value for there respective genders and if not then genderValues should
            // give compile-time error
            preference[genderKey] = Persistent.genderValues.getValue(gender)
        }
    }
    
    override suspend fun saveAge(age: Int) {
        dataStore.edit { preference ->
            preference[ageKey] = age
        }
    }
    
    override suspend fun saveWeight(weight: Float) {
        dataStore.edit { preference ->
            preference[weightKey] = weight
        }
    }
    
    /**
     *  Value are stored as string in preference extracted from [Persistent.weightUnitValues]
     *  for there respective [WeightUnit]
     */
    override suspend fun saveWeightUnit(unit: WeightUnit) {
        dataStore.edit { preference ->
            // Here getValue should not throw NoSuchElementException as genderValues have
            // all the value for there respective genders and if not then genderValues should
            // give compile-time error
            preference[weightUnitKey] = Persistent.weightUnitValues.getValue(unit)
        }
    }
    
    override suspend fun saveBedTime(time: LocalTime) {
        
        
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        val timeString = time.format(formatter)
        
        
        dataStore.edit { preference ->
            preference[bedTimeKey] = timeString
        }
    }
    
    override suspend fun saveWakeupTime(time: LocalTime) {
        
        
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        val timeString = time.format(formatter)
        
        
        dataStore.edit { preference ->
            preference[wakeTimeKey] = timeString
        }
    }
    
    /**
     *  Value are stored as string in preference extracted from [Persistent.waterUnitValues]
     *  for there respective [WaterUnit]
     */
    override suspend fun saveWaterUnit(unit: WaterUnit) {
        dataStore.edit { preference ->
            // Here getValue should not throw NoSuchElementException as waterUnitValues have
            // all the value for there respective WaterUnit and if not then waterUnitValues should
            // give compile-time error
            preference[waterUnitKey] = Persistent.waterUnitValues.getValue(unit)
        }
    }
    
    override suspend fun saveSelectedTrackableDrinkId(id: Long) {
        dataStore.edit { preference ->
            preference[selectedTrackableDrinkIdKey] = id
        }
    }
    
    override suspend fun saveDailyWaterIntakeGoal(amount: Double) {
        dataStore.edit { preference ->
            preference[dailyWaterIntakeGoalKey] = amount
        }
    }
    
    override suspend fun saveIsOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preference ->
            preference[isOnboardingCompletedKey] = completed
        }
    }
    
    
    override fun getGender(): Flow<Gender> = dataStore.data.map { preference ->
        val reveredGenderValues = Persistent.genderValues.entries.associateBy({ it.value }) { it.key }
        reveredGenderValues.getOrDefault(preference[genderKey] ?: "", Constants.DEFAULT_GENDER)
    }
    
    override fun getAge(): Flow<Int> = dataStore.data.map { preference ->
        preference[ageKey] ?: Constants.DEFAULT_AGE
    }
    
    override fun getWeight(): Flow<Float> = dataStore.data.map { preference ->
        preference[weightKey] ?: Constants.DEFAULT_WEIGHT
    }
    
    override fun getWeightUnit(): Flow<WeightUnit> = dataStore.data.map { preference ->
        val reveredWeightUnitValues = Persistent.weightUnitValues.entries.associateBy({ it.value }) { it.key }
        reveredWeightUnitValues.getOrDefault(preference[weightUnitKey] ?: "", Constants.DEFAULT_WEIGHT_UNIT)
    }
    
    @Suppress("KotlinConstantConditions")
    override fun getBedTime(): Flow<LocalTime> = dataStore.data.map { preference ->
        val defaultTime = buildString {
            append(
                if (Constants.BED_TIME_DEFAULT_HOUR < MIN_TWO_DIGIT_NUMBER) "0${Constants.BED_TIME_DEFAULT_HOUR}"
                else Constants.BED_TIME_DEFAULT_HOUR)
            append(":")
            append(
                if (Constants.BED_TIME_DEFAULT_MINUTE < MIN_TWO_DIGIT_NUMBER) "0${Constants.BED_TIME_DEFAULT_MINUTE}"
                else Constants.BED_TIME_DEFAULT_MINUTE
            )
        }
        
        
        val defaultTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        
        
        try {
            LocalTime.parse(preference[bedTimeKey] ?: LocalTime.parse(defaultTime, defaultTimeFormatter).toString())
        } catch (e: DateTimeParseException) {
            LocalTime.parse(defaultTime, defaultTimeFormatter)
        }
    }
    
    @Suppress("KotlinConstantConditions")
    override fun getWakeupTime(): Flow<LocalTime> = dataStore.data.map { preference ->
        val defaultTime = buildString {
            append(
                if (Constants.WAKE_TIME_DEFAULT_HOUR < MIN_TWO_DIGIT_NUMBER) "0${Constants.WAKE_TIME_DEFAULT_HOUR}"
                else Constants.WAKE_TIME_DEFAULT_HOUR
            )
            append(":")
            append(
                if (Constants.WAKE_TIME_DEFAULT_MINUTE < MIN_TWO_DIGIT_NUMBER) "0${Constants.WAKE_TIME_DEFAULT_MINUTE}"
                else Constants.WAKE_TIME_DEFAULT_MINUTE
            )
        }
        
        
        val defaultTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        
        
        try {
            LocalTime.parse(preference[wakeTimeKey] ?: LocalTime.parse(defaultTime, defaultTimeFormatter).toString())
        } catch (e: DateTimeParseException) {
            LocalTime.parse(defaultTime, defaultTimeFormatter)
        }
    }
    
    override fun getWaterUnit(): Flow<WaterUnit> = dataStore.data.map { preference ->
        val reveredWaterUnitValues = Persistent.waterUnitValues.entries.associateBy({ it.value }) { it.key }
        reveredWaterUnitValues.getOrDefault(preference[waterUnitKey] ?: "", Constants.DEFAULT_WATER_UNIT)
    }
    
    override fun getSelectedTrackableDrinkId(): Flow<Long> = dataStore.data.map { preference ->
        preference[selectedTrackableDrinkIdKey] ?: Constants.DEFAULT_SELECTED_TRACKABLE_DRINK_ID
    }
    
    override fun getDailyWaterIntakeGoal(): Flow<Double> = dataStore.data.map { preference ->
        preference[dailyWaterIntakeGoalKey] ?: Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL
    }
    
    override fun getIsOnboardingCompleted(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[isOnboardingCompletedKey] ?: Constants.DEFAULT_IS_ONBOARDING_COMPLETED
    }
    
    
}