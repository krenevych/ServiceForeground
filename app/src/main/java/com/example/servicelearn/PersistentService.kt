package com.example.servicelearn

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PersistentService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isRunning = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "onStartCommand: $this")

        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        isRunning = false
    }

    private fun start() {
        Log.e(TAG, "onStartCommand: Persistent Service")

        if (isRunning) return
        isRunning = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(1, notification(), FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            startForeground(1, notification())
        }

        coroutineScope.launch {
            do {
                Log.d(TAG, "service is running")
                delay(1000)
            }  while (isRunning)
            Log.d(TAG, "service has finished")
        }

    }

    private fun notification(): Notification {
        val notification = NotificationCompat.Builder(this, CHANNEL_PERSISTENT_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Foreground Service")
            .setContentText("This service cannot be swiped away.")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true)
            .build()
        return notification
    }

    override fun onDestroy() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        coroutineScope.cancel()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context, action: Actions): Intent {
            return Intent(context, PersistentService::class.java).apply {
                setAction(action.toString())
            }
        }
    }
}
