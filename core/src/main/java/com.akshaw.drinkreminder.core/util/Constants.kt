package com.akshaw.drinkreminder.core.util

object Constants {

    const val KG_TO_LBS = 2.20462262185
    const val LBS_TO_KG = 0.45359237
    const val ML_TO_FLOZ = 0.033814022701843
    const val FLOZ_TO_ML = 29.5735295625

    /** Gender */
    val DEFAULT_GENDER = Gender.Female

    /** Weight */
    const val MAX_WEIGHT = 350
    const val MIN_WEIGHT = 20
    const val DEFAULT_WEIGHT = 75f
    val DEFAULT_WEIGHT_UNIT = WeightUnit.KG

    /** Age */
    const val MAX_AGE = 125
    const val MIN_AGE = 13
    const val DEFAULT_AGE = 20

    /** Bed Time */
    const val BED_TIME_DEFAULT_HOUR = 10   // 1..12
    const val BED_TIME_DEFAULT_MINUTE = 0  // 0..59
    val BED_TIME_DEFAULT_UNIT = TimeUnit.PM

    /** Wake Time */
    const val WAKE_TIME_DEFAULT_HOUR = 6   // 1..12
    const val WAKE_TIME_DEFAULT_MINUTE = 0  // 0..59
    val WAKE_TIME_DEFAULT_UNIT = TimeUnit.AM

    /** Water */
    val DEFAULT_WATER_UNIT = WaterUnit.ML
    val DEFAULT_DAILY_WATER_INTAKE_GOAL = 2300.0

    /** Selected trackable drink id **/
    const val DEFAULT_SELECTED_TRACKABLE_DRINK_ID = -1L

}