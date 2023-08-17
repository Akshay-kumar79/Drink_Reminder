package com.akshaw.drinkreminder.settingspresentation.settings.events

sealed interface ChangeWeightDialogEvent {
    object ShowDialog: ChangeWeightDialogEvent
    object DismissDialog: ChangeWeightDialogEvent
    data class OnWeightChange(val newWeight: Float): ChangeWeightDialogEvent
    object SaveNewWeight: ChangeWeightDialogEvent
}