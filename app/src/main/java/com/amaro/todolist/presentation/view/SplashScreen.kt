package com.amaro.todolist.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.amaro.todolist.R

class SplashScreen: AppCompatActivity() {
    val DELAY_TIME = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setUpDarkMode()
    }

    override fun onResume() {
        super.onResume()
        setUpDelayActivity()
    }

    private fun setUpDelayActivity() {
        Handler().postDelayed(Runnable() {
            startMainActivity()
        }, DELAY_TIME)
    }

    private fun startMainActivity() {
        val intent = Intent(this,  MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun setUpDarkMode() {
        val sharePreference = PreferenceManager.getDefaultSharedPreferences(this)
        val darkModeEnabled = sharePreference.getBoolean(getString(R.string.day_night_key), false)
        if(darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}