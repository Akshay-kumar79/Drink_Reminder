package com.akshaw.drinkreminder.core.domain

interface MyNotificationManager {
    fun notify(myNotification: MyNotification)
    fun createChannel(channel: MyNotificationChannel)
}

sealed class MyNotification(val notificationId: Int, val channel: MyNotificationChannel) {
    object DrinkNotification: MyNotification(2, MyNotificationChannel.DrinkReminderChannel)
}

enum class MyNotificationChannel(val id: String, val channelName: String) {
    DrinkReminderChannel("drink_reminder", "Drink Water Reminder")
}