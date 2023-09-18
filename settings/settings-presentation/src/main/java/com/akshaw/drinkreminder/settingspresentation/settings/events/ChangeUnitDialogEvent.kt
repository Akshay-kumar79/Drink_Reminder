package com.akshaw.drinkreminder.settingspresentation.settings.events

import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit

sealed interface ChangeUnitDialogEvent {
    object ShowDialog: ChangeUnitDialogEvent
    object DismissDialog: ChangeUnitDialogEvent
    data class ChangeSelectedUnit(var waterUnit: WaterUnit? = null, var weightUnit: WeightUnit?= null): ChangeUnitDialogEvent
    object SaveNewUnits: ChangeUnitDialogEvent
}