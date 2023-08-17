package com.akshaw.drinkreminder.settingspresentation.settings.events

sealed interface ChangeAgeDialogEvent{
    object ShowDialog: ChangeAgeDialogEvent
    object DismissDialog: ChangeAgeDialogEvent
    data class OnAgeChange(val newAge: Int): ChangeAgeDialogEvent
    object SaveNewAge: ChangeAgeDialogEvent
}