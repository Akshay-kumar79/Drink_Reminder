package com.akshaw.drinkreminder.core.domain.preferences.elements

import com.akshaw.drinkreminder.core.util.Constants

private const val ML_ = "ml"
private const val FL_OZ_ = "fl oz"

enum class WaterUnit(val text: String, val id: Int) {
    
    /** ids should not have number gap except INVALID WeightUnit */
    ML(ML_, 100),
    FL_OZ(FL_OZ_, 101);
    
    companion object {
        fun fromString(text: String?): WaterUnit {
            return when (text) {
                ML_ -> ML
                FL_OZ_ -> FL_OZ
                else -> Constants.DEFAULT_WATER_UNIT
            }
        }
        
        private fun fromId(id: Int): WaterUnit? {
            return when (id) {
                100 -> ML
                101 -> FL_OZ
                else -> null
            }
        }
        
        fun fromIdToText(id: Int): String? {
            return fromId(id)?.text
        }
        
        fun maxID() = FL_OZ.id
        fun minID() = ML.id
    }
}