package com.akshaw.drinkreminder.core.util

object Constants {

    const val KG_TO_POUND = 2.20462262185

    /** Gender */
    val DEFAULT_GENDER = Gender.Female

    /** Weight */
    const val MAX_WEIGHT = 350
    const val MIN_WEIGHT = 20
    const val DEFAULT_WEIGHT = 75
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

}