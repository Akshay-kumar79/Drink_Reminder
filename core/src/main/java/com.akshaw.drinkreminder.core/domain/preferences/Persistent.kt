package com.akshaw.drinkreminder.core.domain.preferences

import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender
import com.akshaw.drinkreminder.core.domain.preferences.elements.ReminderType
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.preferences.elements.WeightUnit

object Persistent {
    
    val genderValues = Gender.values().associateWith {
        when (it) {
            Gender.Male -> "male"
            Gender.Female -> "female"
        }
    }
    
    val waterUnitValues = WaterUnit.values().associateWith {
        when (it) {
            WaterUnit.ML -> "ml"
            WaterUnit.FL_OZ -> "fl oz"
        }
    }
    
    val weightUnitValues = WeightUnit.values().associateWith {
        when (it) {
            WeightUnit.KG -> "kg"
            WeightUnit.LBS -> "lbs"
        }
    }
    
    val reminderTypeValues = ReminderType.values().associateWith {
        when (it) {
            ReminderType.TSReminder -> "time_specific_reminder"
            ReminderType.AIReminder -> "automatic_interval_reminder"
        }
    }
    
}