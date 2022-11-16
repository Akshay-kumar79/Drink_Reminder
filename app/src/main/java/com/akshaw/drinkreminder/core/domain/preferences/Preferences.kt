package com.akshaw.drinkreminder.core.domain.preferences

import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import java.time.LocalTime


interface Preferences {

    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveWeightUnit(unit: WeightUnit)
    fun saveBedTime(time: LocalTime)
    fun saveWakeupTime(time: LocalTime)
    fun saveWaterUnit(unit: WaterUnit)

    fun loadGender(): Gender
    fun loadAge(): Int
    fun loadWeight(): Float
    fun loadWeightUnit(): WeightUnit
    fun loadBedTime(): LocalTime
    fun loadWakeupTime(): LocalTime
    fun loadWaterUnit(): WaterUnit

    companion object{

        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_WEIGHT = "weight"
        const val KEY_WEIGHT_UNIT = "weight_unit"
        const val KEY_BED_TIME = "bed_time"
        const val KEY_WAKEUP_TIME = "wakeup_time"
        const val KEY_WATER_UNIT = "water_unit"

    }

}