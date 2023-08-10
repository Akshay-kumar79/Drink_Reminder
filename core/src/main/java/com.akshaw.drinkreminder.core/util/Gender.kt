package com.akshaw.drinkreminder.core.util

sealed class Gender(val name: String) {
    object Male : Gender("male")
    object Female : Gender("female")

    companion object {
        fun fromString(name: String): Gender {
            return when (name) {
                "male" -> Male
                "female" -> Female
                else -> Constants.DEFAULT_GENDER
            }
        }
    }
}