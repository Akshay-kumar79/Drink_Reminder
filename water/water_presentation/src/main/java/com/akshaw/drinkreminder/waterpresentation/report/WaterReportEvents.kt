package com.akshaw.drinkreminder.waterpresentation.report

import com.akshaw.drinkreminder.waterdomain.utils.ChartType

sealed interface WaterReportEvent {
    data class OnChartTypeChange(val chartType: ChartType) : WaterReportEvent
    object OnChartLeftClick : WaterReportEvent
    object OnChartRightClick : WaterReportEvent
}