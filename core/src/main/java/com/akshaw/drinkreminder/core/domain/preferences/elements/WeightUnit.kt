package com.akshaw.drinkreminder.core.domain.preferences.elements

private const val KG_ID = 100
private const val LBS_ID = 101

private const val KG_ = "kg"
private const val LBS_ = "lbs"

enum class WeightUnit(val text: String, val id: Int) {

    /** ids should not have number gap*/
     KG(KG_, KG_ID),
     LBS(LBS_, LBS_ID);

    companion object {
        fun fromString(text: String?): WeightUnit? {
            return when (text) {
                KG_ -> KG
                LBS_ -> LBS
                else -> null
            }
        }

        fun fromId(id: Int): WeightUnit? {
            return when (id) {
                KG_ID -> KG
                LBS_ID -> LBS
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