package com.akshaw.drinkreminder

import android.app.Application
import com.akshaw.drinkreminder.core.domain.MyNotificationChannel
import com.akshaw.drinkreminder.core.domain.MyNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp: Application(){
    
    @Inject
    lateinit var myNotificationManager: MyNotificationManager
    
    override fun onCreate() {
        super.onCreate()
        
        myNotificationManager.createChannel(MyNotificationChannel.DrinkReminderChannel)
    }
    
}