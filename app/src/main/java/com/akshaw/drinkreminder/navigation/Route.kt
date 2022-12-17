package com.akshaw.drinkreminder.navigation

//object Route {
//
//    const val ONBOARDING = "onboarding"
//    const val WATER_TRACKER = "water_tracker"
//
//}

sealed class Route(val route: String){
    object WaterHomeScreen: Route("water_home_screen")
    object WaterReportScreen: Route("water_report_screen")
    object WaterADayDrinkScreen: Route("water_a_day_drink_screen")
}