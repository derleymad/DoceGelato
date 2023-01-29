package com.example.docegelato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.docegelato.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashToOtherActivity()
    }

    private fun splashToOtherActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }, 1000)
    }
}