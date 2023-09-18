package com.akshaw.drinkreminder.core.domain.preferences.elements

import com.akshaw.drinkreminder.core.util.Constants

private const val KG_ = "kg"
private const val LBS_ = "lbs"

enum class WeightUnit(val text: String, val id: Int) {

    /** ids should not have number gap*/
     KG(KG_, 100),
     LBS(LBS_, 101);

    companion object {
        fun fromString(text: String?): WeightUnit {
            return when (text) {
                KG_ -> KG
                LBS_ -> LBS
                else -> Constants.DEFAULT_WEIGHT_UNIT
            }
        }

        fun fromId(id: Int): WeightUnit? {
            return when (id) {
                100 -> KG
                101 -> LBS
                else -> null
            }
        }

        fun fromIdToName(id: Int): String? {
            return fromId(id)?.text
        }

        fun maxID() = LBS.id
        fun minID() = KG.id
    }
}