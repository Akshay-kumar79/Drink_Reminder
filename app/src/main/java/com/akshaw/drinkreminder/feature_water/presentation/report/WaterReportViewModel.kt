package com.akshaw.drinkreminder.feature_water.presentation.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.feature_water.domain.use_case.*
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class WaterReportViewModel @Inject constructor(
    filterADayDrinks: FilterADayDrinks,
    getDrinkProgress: GetDrinkProgress,
    getAllDrinks: GetAllDrinks,
    getReportChartData: GetReportChartData,
    isReportChartLeftAvailable: IsReportChartLeftAvailable,
    isReportChartRightAvailable: IsReportChartRightAvailable,
    getWeeklyAverageProgress: GetWeeklyAverageProgress,
    getWeeklyAverageCompletion: GetWeeklyAverageCompletion,
    getWeeklyAverageFrequency: GetWeeklyAverageFrequency
) : ViewModel() {
    
    private val _goal = MutableStateFlow(2343.0)
    val goal = _goal.asStateFlow()
    
    private val allDrinks = getAllDrinks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    val todayProgress = allDrinks.map {
        getDrinkProgress(filterADayDrinks(LocalDate.now(), it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val yesterdayProgress = allDrinks.map {
        getDrinkProgress(filterADayDrinks(LocalDate.now().minusDays(1), it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val dayBeforeYesterdayProgress = allDrinks.map {
        getDrinkProgress(filterADayDrinks(LocalDate.now().minusDays(2), it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    
    // Chart States
    private val _selectedChart = MutableStateFlow(ChartType.WEEK)
    val selectedChart = _selectedChart.asStateFlow()
    
    private val _chartSelectedWeeksFirstDay = MutableStateFlow(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
    val chartSelectedWeeksFirstDay = _chartSelectedWeeksFirstDay.asStateFlow()
    
    private val _chartSelectedYear = MutableStateFlow(Year.now())
    val chartSelectedYear = _chartSelectedYear.asStateFlow()
    
    val chartData = combine(
        allDrinks,
        selectedChart,
        chartSelectedWeeksFirstDay,
        chartSelectedYear,
        goal
    ) { allDrinks, selectedChart, chartSelectedWeeksFirstDay, chartSelectedYear, goal ->
        
        getReportChartData(
            allDrinks,
            selectedChart,
            chartSelectedWeeksFirstDay,
            chartSelectedYear,
            goal
        )
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    val isChartLeftAvailable = combine(
        allDrinks,
        selectedChart,
        chartSelectedWeeksFirstDay,
        chartSelectedYear
    ) { allDrinks, selectedChart, chartSelectedWeeksFirstDay, chartSelectedYear ->
        
        isReportChartLeftAvailable(
            allDrinks,
            selectedChart,
            chartSelectedWeeksFirstDay,
            chartSelectedYear
        )
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    
    val isChartRightAvailable = combine(
        allDrinks,
        selectedChart,
        chartSelectedWeeksFirstDay,
        chartSelectedYear
    ) { allDrinks, selectedChart, chartSelectedWeeksFirstDay, chartSelectedYear ->
        
        isReportChartRightAvailable(
            allDrinks,
            selectedChart,
            chartSelectedWeeksFirstDay,
            chartSelectedYear
        )
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    
    // Average Report States
    val weeklyAverageProgress = allDrinks.map { allDrinks ->
        getWeeklyAverageProgress(allDrinks)
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val weeklyAverageCompletion = combine(allDrinks, goal) { allDrinks, goal ->
        getWeeklyAverageCompletion(allDrinks, goal)
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val weeklyAverageFrequency = allDrinks.map { allDrinks ->
        getWeeklyAverageFrequency(allDrinks)
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
    
    
    // TODO get live changes from preferences
    // TODO load goal from preferences
    
    fun onEvent(event: WaterReportEvent) = viewModelScope.launch {
        when (event) {
            is WaterReportEvent.OnADayProgressClick -> {
                //TODO navigate to A Day Drinks Screen
            }
            is WaterReportEvent.OnChartTypeChange -> {
                _selectedChart.value = event.chartType
            }
            WaterReportEvent.OnChartLeftClick -> {
                when (selectedChart.value) {
                    ChartType.WEEK -> _chartSelectedWeeksFirstDay.value = chartSelectedWeeksFirstDay.value.minusWeeks(1)
                    ChartType.YEAR -> _chartSelectedYear.value = chartSelectedYear.value.minusYears(1)
                }
            }
            WaterReportEvent.OnChartRightClick -> {
                when (selectedChart.value) {
                    ChartType.WEEK -> _chartSelectedWeeksFirstDay.value = chartSelectedWeeksFirstDay.value.plusWeeks(1)
                    ChartType.YEAR -> _chartSelectedYear.value = chartSelectedYear.value.plusYears(1)
                }
            }
        }
    }
    
}