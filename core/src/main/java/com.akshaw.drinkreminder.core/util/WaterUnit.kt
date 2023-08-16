package com.akshaw.drinkreminder.core.util

/**
 * Changing these constant value may cause compatibility issues with older versions of app
 * as these values are stored in local preferences
 */
private const val ML_ = "ml"
private const val FL_OZ_ = "fl oz"
private const val INVALID = "invalid"

sealed class WaterUnit(val name: String, val id: Int) {
    
    /** ids should not have number gap except INVALID WeightUnit */
    object ML : WaterUnit(ML_, 100)
    object FL_OZ : WaterUnit(FL_OZ_, 101)
    object Invalid : WaterUnit(INVALID, -1)
    
    companion object {
        fun fromString(text: String?): WaterUnit {
            return when (text) {
                "ml" -> ML
                "fl oz" -> FL_OZ
                else -> Invalid
            }
        }
        
        fun fromId(id: Int): WaterUnit {
            return when (id) {
                100 -> ML
                101 -> FL_OZ
                else -> Invalid
            }
        }
        
        fun fromIdToName(id: Int): String {
            return fromId(id).name
        }
        
        fun maxID() = FL_OZ.id
        fun minID() = ML.id
    }
}