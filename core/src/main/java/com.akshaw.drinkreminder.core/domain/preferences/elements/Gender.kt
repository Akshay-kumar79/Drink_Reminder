package com.akshaw.drinkreminder.core.domain.preferences.elements

enum class Gender {
    Male,
    Female;
    
    companion object {
        fun fromString(name: String): Gender? {
            return when (name) {
                Male.name -> Male
                Female.name -> Female
                else -> null
            }
        }
    }
}