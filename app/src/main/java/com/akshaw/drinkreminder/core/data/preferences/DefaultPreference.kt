package com.akshaw.drinkreminder.core.data.preferences

import android.content.SharedPreferences
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WeightUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DefaultPreference(val sharedPref: SharedPreferences) : Preferences {

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

    override fun loadGender(): Gender {
        return Gender.fromString(sharedPref.getString(Preferences.KEY_GENDER, null))
    }

    override fun loadAge(): Int {
        return sharedPref.getInt(Preferences.KEY_AGE, 20)
    }

    override fun loadWeight(): Float {
        return sharedPref.getFloat(Preferences.KEY_WEIGHT, 75f)
    }

    override fun loadWeightUnit(): WeightUnit {
        return WeightUnit.fromString(sharedPref.getString(Preferences.KEY_WEIGHT_UNIT, WeightUnit.KG.name))
    }

    override fun loadBedTime(): LocalTime {
        return LocalTime.parse(sharedPref.getString(Preferences.KEY_BED_TIME, "22:00"))
    }

    override fun loadWakeupTime(): LocalTime {
        return LocalTime.parse(sharedPref.getString(Preferences.KEY_WAKEUP_TIME, "07:00"))
    }
}