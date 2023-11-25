package com.akshaw.drinkreminder.navigation

import androidx.annotation.DrawableRes
import com.akshaw.drinkreminder.core.R

sealed class BottomNavRoute(
    val title: String,
    @DrawableRes val iconResId: Int,
    val page: Int
) {
    object WaterHomeScreen : BottomNavRoute("Home", R.drawable.bottom_nav_home, 0)
    object WaterReportScreen : BottomNavRoute("Report", R.drawable.bottom_nav_report, 1)
    object SettingsScreen : BottomNavRoute("Settings", R.drawable.bottom_nav_settings, 2)
}
