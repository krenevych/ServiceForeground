package com.example.servicelearn

import android.app.Service
import android.content.Intent
import android.util.Log


class MyService : Service() {
    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val stringExtra = intent?.getStringExtra(KEY_1)

        Log.d(TAG, "onStartCommand: Service, KEY = $stringExtra")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Service")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy: Service")
    }
}