package com.amaro.todolist.presentation.view.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.amaro.todolist.R
import com.amaro.todolist.domain.log.Logger
import org.koin.android.ext.android.inject

class SettingsFragment : PreferenceFragmentCompat() {
    private val TAG = SettingsFragment::class.java.simpleName
    private val mLogger: Logger by inject()
    private var dayNightSwitch: SwitchPreferenceCompat? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        dayNightSwitch = findPreference(getString(R.string.day_night_key))
        setUpDarkModeSwitch()
    }

    private fun setUpDarkModeSwitch() {
        dayNightSwitch?.summaryOn = getString(R.string.enabled)
        dayNightSwitch?.summaryOff = getString(R.string.disabled)
        dayNightSwitch?.setOnPreferenceChangeListener { _, newValue ->
            mLogger.i(TAG, "darkMode enable: $newValue")
            enableDarkMode(newValue as Boolean)
            true
        }
    }

    private fun enableDarkMode(enable: Boolean) {
        if(enable) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}