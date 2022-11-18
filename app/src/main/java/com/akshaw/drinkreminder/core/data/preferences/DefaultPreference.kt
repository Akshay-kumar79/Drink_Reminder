package com.akshaw.drinkreminder.core.data.preferences

import android.content.SharedPreferences
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DefaultPreference(private val sharedPref: SharedPreferences) : Preferences {

    override fun saveGender(gender: Gender) {
        sharedPref.edit().putString(Preferences.KEY_GENDER, gender.name).apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit().putInt(Preferences.KEY_AGE, age).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit().putFloat(Preferences.KEY_WEIGHT, weight).apply()
    }

    override fun saveWeightUnit(unit: WeightUnit) {
        sharedPref.edit().putString(Preferences.KEY_WEIGHT_UNIT, unit.name).apply()
    }

    override fun saveBedTime(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        val timeString = time.format(formatter)

        sharedPref.edit().putString(Preferences.KEY_BED_TIME, timeString).apply()
    }

    override fun saveWakeupTime(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        val timeString = time.format(formatter)

        sharedPref.edit().putString(Preferences.KEY_WAKEUP_TIME, timeString).apply()
    }

    override fun saveWaterUnit(unit: WaterUnit) {
        sharedPref.edit().putString(Preferences.KEY_WATER_UNIT, unit.name).apply()
    }

    override fun saveSelectedTrackableDrinkId(id: Long) {
        sharedPref.edit().putLong(Preferences.KEY_SELECTED_TRACKABLE_DRINK_ID, id).apply()
    }


    override fun loadGender(): Gender {
        return Gender.fromString(sharedPref.getString(Preferences.KEY_GENDER, Constants.DEFAULT_GENDER.name)!!)
    }

    override fun loadAge(): Int {
        return sharedPref.getInt(Preferences.KEY_AGE, Constants.DEFAULT_AGE)
    }

    override fun loadWeight(): Float {
        return sharedPref.getFloat(Preferences.KEY_WEIGHT, Constants.DEFAULT_WEIGHT.toFloat())
    }

    override fun loadWeightUnit(): WeightUnit {
        return WeightUnit.fromString(sharedPref.getString(Preferences.KEY_WEIGHT_UNIT, Constants.DEFAULT_WEIGHT_UNIT.name))
    }

    //TODO change time to sync from Constants files
    //TODO catch parsing exception

    override fun loadBedTime(): LocalTime {
        return LocalTime.parse(sharedPref.getString(Preferences.KEY_BED_TIME, "22:00"))
    }

    override fun loadWakeupTime(): LocalTime {
        return LocalTime.parse(sharedPref.getString(Preferences.KEY_WAKEUP_TIME, "07:00"))
    }

    override fun loadWaterUnit(): WaterUnit {
        return WaterUnit.fromString(sharedPref.getString(Preferences.KEY_WATER_UNIT, Constants.DEFAULT_WATER_UNIT.name))
    }

    override fun loadSelectedTrackableDrinkId(): Long {
        return sharedPref.getLong(Preferences.KEY_SELECTED_TRACKABLE_DRINK_ID, Constants.DEFAULT_SELECTED_TRACKABLE_DRINK_ID)
    }
}