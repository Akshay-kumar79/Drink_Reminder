package com.akshaw.drinkreminder.waterpresentation.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.waterdomain.use_case.*
import com.akshaw.drinkreminder.waterdomain.utils.ChartType
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.Drink
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
    preferences: Preferences,
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
    
    val goal = preferences.getDailyWaterIntakeGoal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_DAILY_WATER_INTAKE_GOAL)
    
    val waterUnit = preferences.getWaterUnit()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Constants.DEFAULT_WATER_UNIT)
    
    private val allDrinks = getAllDrinks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    val todayProgress = combine(allDrinks, waterUnit) { allDrinks, waterUnit ->
        getDrinkProgress(filterADayDrinks(LocalDate.now(), allDrinks), waterUnit)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val yesterdayProgress = combine(allDrinks, waterUnit) { allDrinks, waterUnit ->
        getDrinkProgress(filterADayDrinks(LocalDate.now().minusDays(1), allDrinks), waterUnit)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val dayBeforeYesterdayProgress = combine(allDrinks, waterUnit) { allDrinks, waterUnit ->
        getDrinkProgress(filterADayDrinks(LocalDate.now().minusDays(2), allDrinks), waterUnit)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    
    // Chart States
    private val _selectedChart = MutableStateFlow(ChartType.WEEK)
    val selectedChart = _selectedChart.asStateFlow()
    
    private val _chartSelectedWeeksFirstDay = MutableStateFlow(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
    val chartSelectedWeeksFirstDay = _chartSelectedWeeksFirstDay.asStateFlow()
    
    private val _chartSelectedYear = MutableStateFlow(Year.now())
    val chartSelectedYear = _chartSelectedYear.asStateFlow()
    
    @Suppress("MagicNumber")
    val chartData = combine(
        allDrinks,
        selectedChart,
        chartSelectedWeeksFirstDay,
        chartSelectedYear,
        goal,
        waterUnit
    ) { values ->
        
        val allDrinks = values[0] as List<Drink>
        val selectedChart = values[1] as ChartType
        val chartSelectedWeeksFirstDay = values[2] as LocalDate
        val chartSelectedYear = values[3] as Year
        val goal = values[4] as Double
        val waterUnit = values[5] as WaterUnit
        
        getReportChartData(
            allDrinks,
            selectedChart,
            chartSelectedWeeksFirstDay,
            chartSelectedYear,
            goal,
            waterUnit
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
    val weeklyAverageProgress = combine(allDrinks, waterUnit) { allDrinks, waterUnit ->
        getWeeklyAverageProgress(allDrinks, waterUnit)
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val weeklyAverageCompletion = combine(allDrinks, goal, waterUnit) { allDrinks, goal, waterUnit ->
        getWeeklyAverageCompletion(allDrinks, goal, waterUnit)
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val weeklyAverageFrequency = allDrinks.map { allDrinks ->
        getWeeklyAverageFrequency(allDrinks)
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
    
    
    fun onEvent(event: WaterReportEvent) = viewModelScope.launch {
        when (event) {
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