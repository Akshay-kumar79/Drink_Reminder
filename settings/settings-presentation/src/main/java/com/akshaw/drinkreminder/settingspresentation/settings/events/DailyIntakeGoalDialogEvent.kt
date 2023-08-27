package com.akshaw.drinkreminder.settingspresentation.settings.events

sealed interface DailyIntakeGoalDialogEvent {
    
    object ShowDialog : DailyIntakeGoalDialogEvent
    object DismissDialog : DailyIntakeGoalDialogEvent
    data class OnDailyIntakeGoalChange(val newIntake: Double) : DailyIntakeGoalDialogEvent
    object OnReset: DailyIntakeGoalDialogEvent
    object SaveDailyIntakeGoal : DailyIntakeGoalDialogEvent
    
}