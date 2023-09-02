package com.akshaw.drinkreminder.core.util

object Constants {
    
    const val KG_TO_LBS = 2.20462262185
    const val LBS_TO_KG = 0.45359237
    const val ML_TO_FLOZ = 0.033814022701843
    const val FLOZ_TO_ML = 29.5735295625
    
    /** Gender */
    val DEFAULT_GENDER = Gender.Female
    
    /** Weight */
    const val MAX_WEIGHT = 350  // in Kg
    const val MIN_WEIGHT = 20  // in kg
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
    const val MAX_DAILY_WATER_INTAKE_GOAL_ML = 10000.0
    const val MAX_DAILY_WATER_INTAKE_GOAL_FL_OZ = 10000.0 * ML_TO_FLOZ
    const val MIN_DAILY_WATER_INTAKE_GOAL_ML = 100.0
    const val MIN_DAILY_WATER_INTAKE_GOAL_FL_OZ = 100.0 * ML_TO_FLOZ
    const val DEFAULT_DAILY_WATER_INTAKE_GOAL = 2300.0
    
    /** Selected trackable drink id **/
    const val DEFAULT_SELECTED_TRACKABLE_DRINK_ID = -1L
    
    /** Onboarding **/
    const val DEFAULT_IS_ONBOARDING_COMPLETED = false
    
    /** Default trackable drinks */
    val DEFAULT_TRACKABLE_DRINKS = listOf(50.0, 100.0, 200.0, 300.0, 500.0) // in ml
    
    // Privacy policy
    const val PRIVACY_POLICY_URL = "https://www.freeprivacypolicy.com/live/0ad33e23-bc02-41bd-8ded-2f65c1929dc6"
    
}