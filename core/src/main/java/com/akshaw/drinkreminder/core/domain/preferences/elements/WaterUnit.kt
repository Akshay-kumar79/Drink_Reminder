package com.akshaw.drinkreminder.core.domain.preferences.elements

/** ids should not have number gap*/
private const val ML_ID = 100
private const val FL_OZ_ID = 101

private const val ML_ = "ml"
private const val FL_OZ_ = "fl oz"

enum class WaterUnit(val text: String, val id: Int) {
    
    ML(ML_, ML_ID),
    FL_OZ(FL_OZ_, FL_OZ_ID);
    
    companion object {
        fun fromString(text: String?): WaterUnit? {
            return when (text) {
                ML_ -> ML
                FL_OZ_ -> FL_OZ
                else -> null
            }
        }
    }
}