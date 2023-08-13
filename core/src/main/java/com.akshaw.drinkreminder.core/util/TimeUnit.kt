package com.akshaw.drinkreminder.core.util

/**
 * Changing these constant value may cause compatibility issues with older versions of app
 * as these values are stored in local preferences
 */
private const val A_M = "AM"
private const val P_M = "PM"

sealed class TimeUnit(val name: String, val id: Int) {
    
    /** ids should not have number gap except INVALID WeightUnit (for number picker purpose) */
    object AM : TimeUnit(A_M, 100)
    object PM : TimeUnit(P_M, 101)
    object INVALID : TimeUnit("invalid", -1)

    companion object {
        fun fromString(text: String?): TimeUnit {
            return when (text) {
                A_M -> AM
                P_M -> PM
                else -> INVALID
            }
        }

        fun fromId(id: Int): TimeUnit {
            return when (id) {
                100 -> AM
                101 -> PM
                else -> INVALID
            }
        }

        fun fromIdToName(id: Int): String {
            return fromId(id).name
        }

        fun maxID() = PM.id
        fun minID() = AM.id
    }
}
