package com.akshaw.drinkreminder.core.util


sealed class WeightUnit(val name: String, val id: Int) {

    /** ids should not have number gap except INVALID WeightUnit */
    object KG : WeightUnit("kg", 100)
    object LBS : WeightUnit("lbs", 101)
    object INVALID : WeightUnit("invalid", -1)

    companion object {
        fun fromString(text: String?): WeightUnit {
            return when (text) {
                "kg" -> KG
                "lbs" -> LBS
                else -> INVALID
            }
        }

        fun fromId(id: Int): WeightUnit {
            return when (id) {
                100 -> KG
                101 -> LBS
                else -> INVALID
            }
        }

        fun fromIdToName(id: Int): String {
            return fromId(id).name
        }

        fun maxID() = LBS.id
        fun minID() = KG.id
    }
}