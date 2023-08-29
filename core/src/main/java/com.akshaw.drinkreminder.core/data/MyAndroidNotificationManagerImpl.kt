package com.akshaw.drinkreminder.core.data

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.MyNotification
import com.akshaw.drinkreminder.core.domain.MyNotificationChannel
import com.akshaw.drinkreminder.core.domain.MyNotificationManager


class MyAndroidNotificationManagerImpl(
    private val context: Application
) : MyNotificationManager {
    
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    override fun notify(myNotification: MyNotification) {
        
        val resultIntent = Intent(context, Class.forName("com.akshaw.drinkreminder.MainActivity"))
        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = when (myNotification) {
            is MyNotification.DrinkNotification -> {
                Notification.Builder(context, myNotification.channel.id)
                    .setContentTitle("REminder")
                    .setContentText("It's time to have some water")
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(Icon.createWithResource(context, R.drawable.water_icon))
                    .build()
            }
        }
        
        notificationManager.notify(myNotification.notificationId, notification)
    }
    
    override fun createChannel(channel: MyNotificationChannel) {
        val notificationChannel = NotificationChannel(channel.id, channel.channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}