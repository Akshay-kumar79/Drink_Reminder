package com.akshaw.drinkreminder.core.util

/**
 * Changing these constant value may cause compatibility issues with older versions of app
 * as these values are stored in local preferences
 */
private const val MALE = "male"
private const val FEMALE = "female"
private const val INVALID = "invalid"

sealed class Gender(val name: String) {
    object Male : Gender(MALE)
    object Female : Gender(FEMALE)

    companion object {
        fun fromString(name: String): Gender {
            return when (name) {
                MALE -> Male
                FEMALE -> Female
                else -> Constants.DEFAULT_GENDER
            }
        }
    }
}
