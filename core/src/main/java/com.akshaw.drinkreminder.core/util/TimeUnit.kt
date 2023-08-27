package com.akshaw.drinkreminder.core.util


sealed class TimeUnit(val name: String, val id: Int) {
    
    /** ids should not have number gap except INVALID WeightUnit (for number picker purpose) */
    object AM : TimeUnit("AM", 100)
    object PM : TimeUnit("PM", 101)
    object INVALID : TimeUnit("invalid", -1)

    companion object {
        fun fromString(text: String?): TimeUnit {
            return when (text) {
                "AM" -> AM
                "PM" -> PM
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
