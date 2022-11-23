package com.akshaw.drinkreminder.core.util

sealed class WaterUnit(val name: String, val id: Int) {

    /** ids should not have number gap except INVALID WeightUnit */
    object ML : WaterUnit("ml", 100)
    object FL_OZ : WaterUnit("fl oz", 101)
    object INVALID : WaterUnit("invalid", -1)

    companion object {
        fun fromString(text: String?): WaterUnit {
            return when (text) {
                "ml" -> ML
                "fl oz" -> FL_OZ
                else -> INVALID
            }
        }

        fun fromId(id: Int): WaterUnit {
            return when (id) {
                100 -> ML
                101 -> FL_OZ
                else -> INVALID
            }
        }

        fun fromIdToName(id: Int): String {
            return fromId(id).name
        }

        fun maxID() = FL_OZ.id
        fun minID() = ML.id
    }
}
