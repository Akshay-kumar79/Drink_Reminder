package com.akshaw.drinkreminder.core.util

sealed class TimeUnit(val name: String) {
    object AM : TimeUnit("AM")
    object PM : TimeUnit("PM")

    companion object {
        fun fromString(text: String?): TimeUnit {
            return when (text) {
                "AM" -> AM
                "PM" -> PM
                else -> AM
            }
        }
    }
}

sealed class WeightUnit(val name: String) {
    object KG : WeightUnit("kg")
    object LBS : WeightUnit("lbs")

    companion object {
        fun fromString(text: String?): WeightUnit {
            return when (text) {
                "kg" -> KG
                "lbs" -> LBS
                else -> KG
            }
        }
    }
}