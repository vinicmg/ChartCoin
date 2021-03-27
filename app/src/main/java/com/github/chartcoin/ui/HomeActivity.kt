package com.github.chartcoin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.chartcoin.R
import com.github.chartcoin.ui.main.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commitNow()
        }
    }
}