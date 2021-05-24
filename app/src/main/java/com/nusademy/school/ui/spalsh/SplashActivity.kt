package com.nusademy.school.ui.spalsh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nusademy.school.R
import com.nusademy.school.databinding.ActivitySplashBinding
import com.nusademy.school.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        binding = ActivitySplashBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity ::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}