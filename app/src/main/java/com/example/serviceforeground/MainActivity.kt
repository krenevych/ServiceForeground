package com.example.serviceforeground

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.serviceforeground.PersistentService.Companion.intent
import com.example.serviceforeground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermissions()

        binding.buttonRun.setOnClickListener {
            startService(intent(this@MainActivity, Actions.START))
        }

        binding.buttonStop.setOnClickListener {
            startService(intent(this@MainActivity, Actions.STOP))
        }


    }

    private fun checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC),
                1
            )

    }
}