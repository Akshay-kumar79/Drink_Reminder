package com.akshaw.drinkreminder.waterpresentation.report

import com.akshaw.drinkreminder.core.util.ChartType
import java.time.LocalDate

sealed interface WaterReportEvent{
    data class OnADayProgressClick(val data: LocalDate): WaterReportEvent
    data class OnChartTypeChange(val chartType: ChartType): WaterReportEvent
    object OnChartLeftClick: WaterReportEvent
    object OnChartRightClick: WaterReportEvent
}