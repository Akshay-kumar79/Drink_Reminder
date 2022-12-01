package com.akshaw.drinkreminder.feature_water.presentation.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.feature_water.domain.use_case.FilterADayDrinks
import com.akshaw.drinkreminder.feature_water.domain.use_case.FilterAMonthDrink
import com.akshaw.drinkreminder.feature_water.domain.use_case.GetAllDrinks
import com.akshaw.drinkreminder.feature_water.domain.use_case.GetDrinkProgress
import com.akshaw.drinkreminder.feature_water.utils.ChartType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.*
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class WaterReportViewModel @Inject constructor(
    preferences: Preferences,
    filterADayDrinks: FilterADayDrinks,
    filterAMonthDrink: FilterAMonthDrink,
    getDrinkProgress: GetDrinkProgress,
    getAllDrinks: GetAllDrinks,
) : ViewModel() {
    
    private val _goal = MutableStateFlow(2343.0)
    val goal = _goal.asStateFlow()
    
    val allDrinks = getAllDrinks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    
    val todayProgress = allDrinks.map {
        getDrinkProgress(filterADayDrinks(LocalDate.now(), it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val yesterdayProgress = allDrinks.map {
        getDrinkProgress(filterADayDrinks(LocalDate.now().minusDays(1), it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    val dayBeforeYesterdayProgress = allDrinks.map {
        getDrinkProgress(filterADayDrinks(LocalDate.now().minusDays(2), it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
    
    private val _selectedChart = MutableStateFlow(ChartType.WEEK)
    val selectedChart = _selectedChart.asStateFlow()
    
    private val _chartCurrentWeeksFirstDay = MutableStateFlow(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
    val chartCurrentWeeksFirstDay = _chartCurrentWeeksFirstDay.asStateFlow()
    
    private val _chartCurrentYear = MutableStateFlow(Year.now())
    val chartCurrentYear = _chartCurrentYear.asStateFlow()
    
    val chartData = combine(allDrinks, selectedChart, chartCurrentWeeksFirstDay, chartCurrentYear) { allDrinks, selectedChart, chartCurrentWeeksFirstDay, chartCurrentYear ->
        
        val data = mutableListOf<Double>()
        when (selectedChart) {
            ChartType.WEEK -> {
                DayOfWeek.values().forEach {
                    data.add(
                        getDrinkProgress(filterADayDrinks(
                            chartCurrentWeeksFirstDay.plusDays(it.value - 1L), allDrinks
                        ))
                    )
                }
            }
            ChartType.YEAR -> {
                Month.values().forEach {
                    data.add(
                        getDrinkProgress(filterAMonthDrink(
                            YearMonth.of(chartCurrentYear.value, it), allDrinks
                        ))
                    )
                }
            }
        }
        data
        
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList<Double>())
    
    // TODO get live changes from preferences
    // TODO load goal from preferences
    init {
    
    }
    
}