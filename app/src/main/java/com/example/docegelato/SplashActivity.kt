package com.example.docegelato

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.docegelato.databinding.ActivitySplashBinding
import com.example.docegelato.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashToOtherActivity()
    }

    private fun splashToOtherActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }, 1000)
    }
}