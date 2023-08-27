package com.akshaw.drinkreminder.core.util

/**
 * Changing these constant value may cause compatibility issues with older versions of app
 * as these values are stored in local preferences
 */
private const val KG_ = "kg"
private const val LBS_ = "lbs"
private const val INVALID = "invalid"

sealed class WeightUnit(val name: String, val id: Int) {

    /** ids should not have number gap except INVALID WeightUnit */
    object KG : WeightUnit(KG_, 100)
    object LBS : WeightUnit(LBS_, 101)
    object Invalid : WeightUnit(INVALID, -1)

    companion object {
        fun fromString(text: String?): WeightUnit {
            return when (text) {
                KG_ -> KG
                LBS_ -> LBS
                else -> Invalid
            }
        }

        fun fromId(id: Int): WeightUnit {
            return when (id) {
                100 -> KG
                101 -> LBS
                else -> Invalid
            }
        }

        fun fromIdToName(id: Int): String {
            return fromId(id).name
        }

        fun maxID() = LBS.id
        fun minID() = KG.id
    }
}