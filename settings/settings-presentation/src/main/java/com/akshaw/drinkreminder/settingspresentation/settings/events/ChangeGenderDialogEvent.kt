package com.akshaw.drinkreminder.settingspresentation.settings.events

import com.akshaw.drinkreminder.core.domain.preferences.elements.Gender

sealed interface ChangeGenderDialogEvent {
    
    object ShowDialog: ChangeGenderDialogEvent
    object DismissDialog: ChangeGenderDialogEvent
    data class SaveNewGender(val gender: Gender): ChangeGenderDialogEvent
}