package com.example.servicelearn

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class ServiceApp : Application() {


    override fun onCreate() {
        super.onCreate()

        createChannel(
            CHANNEL_PERSISTENT_ID,
            CHANNEL_PERSISTENT_NAME,
        )
    }

    private fun createChannel(id: String, name: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val channel = NotificationChannel(
                 id,
                 name,
                NotificationManager.IMPORTANCE_HIGH
            ).also {
                it.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) it.isBlockable = true
            }

             notificationManager.createNotificationChannel(channel)

        }
    }

}