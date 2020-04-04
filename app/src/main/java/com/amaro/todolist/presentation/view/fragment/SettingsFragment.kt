package com.amaro.todolist.presentation.view.fragment

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        setUpActionBar()
        setUpDarkModeSwitch()
    }

    private fun setUpDarkModeSwitch() {
        dayNightSwitch?.apply {
            summaryOn = getString(R.string.enabled)
            summaryOff = getString(R.string.disabled)
            setOnPreferenceChangeListener { _, newValue ->
                mLogger.i(TAG, "darkMode enable: $newValue")
                enableDarkMode(newValue as Boolean)
                true
            }
        }
    }

    private fun setUpActionBar() {
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        val toolbar = actionBar?.customView
        val toolbarTitle = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle?.text = getString(R.string.settings_title)
    }

    private fun enableDarkMode(enable: Boolean) {
        if(enable) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}