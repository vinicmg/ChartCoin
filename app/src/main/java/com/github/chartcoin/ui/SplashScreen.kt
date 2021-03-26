package com.github.chartcoin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.github.chartcoin.R

class SplashScreen : AppCompatActivity() {
    private var TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        callHomeScreen()
    }

    private fun callHomeScreen() {
        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, TIME_OUT)
    }
}