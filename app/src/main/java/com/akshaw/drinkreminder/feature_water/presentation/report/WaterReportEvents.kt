package com.akshaw.drinkreminder.feature_water.presentation.report

import com.akshaw.drinkreminder.feature_water.utils.ChartType
import java.time.LocalDate

sealed interface WaterReportEvent{
    data class OnADayProgressClick(val data: LocalDate): WaterReportEvent
    data class OnChartTypeChange(val chartType: ChartType): WaterReportEvent
    object OnChartLeftClick: WaterReportEvent
    object OnChartRightClick: WaterReportEvent
}