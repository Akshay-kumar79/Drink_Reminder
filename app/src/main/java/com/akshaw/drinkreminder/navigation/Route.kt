package com.akshaw.drinkreminder.navigation

//object Route {
//
//    const val ONBOARDING = "onboarding"
//    const val WATER_TRACKER = "water_tracker"
//
//}

sealed class Route(val route: String){
    object OnboardingScreen: Route("onboarding_screen")
    object WaterHomeScreen: Route("water_home_screen")
    object WaterReportScreen: Route("water_report_screen")
    object WaterADayDrinkScreen: Route("water_a_day_drink_screen")
    object WaterReminderScreen: Route("water_reminder_screen")
    object SettingsScreen: Route("settings_screen")
    object SettingsFaqScreen: Route("settings_faq_Screen")
    object SettingsBugReportScreen: Route("settings_bug_report_screen")
}