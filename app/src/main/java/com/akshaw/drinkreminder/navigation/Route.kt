package com.akshaw.drinkreminder.navigation

sealed class Route(val route: String) {
    object OnboardingScreen : Route("onboarding_screen")
    object MainScreen: Route("main_screen")
    object WaterADayDrinkScreen : Route("water_a_day_drink_screen")
    object WaterReminderScreen : Route("water_reminder_screen")
    object SettingsFaqScreen : Route("settings_faq_Screen")
    object SettingsBugReportScreen : Route("settings_bug_report_screen")
}