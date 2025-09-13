package com.dertefter.somestuffwithfilms

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewGroupCompat
import androidx.core.view.WindowCompat
import com.dertefter.somestuffwithfilms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.enableEdgeToEdge(window)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
        if (Build.VERSION.SDK_INT >= 29) {
            window.setNavigationBarContrastEnforced(true)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewGroupCompat.installCompatInsetsDispatch(binding.root)


    }
}
