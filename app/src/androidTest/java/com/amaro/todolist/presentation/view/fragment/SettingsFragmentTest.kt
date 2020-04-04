package com.amaro.todolist.presentation.view.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.navigation.NavDeepLinkBuilder
import androidx.preference.PreferenceViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.amaro.todolist.R
import com.amaro.todolist.presentation.view.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsFragmentTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private lateinit var sharePreference : SharedPreferences

    @Before
    fun setUp() {
        sharePreference = PreferenceManager.getDefaultSharedPreferences(activityRule.activity)
        launchFragment(R.id.settingsFragment, null)
    }

    @Test
    fun checkSettingsTitle() {
        onView(withText(R.string.settings_title)).check(matches(isDisplayed()));
    }

    @Test
    fun changeDarkMode() {
        val darkModeValue = sharePreference.getBoolean(activityRule.activity.getString(R.string.day_night_key), false)

        onView(withId(androidx.preference.R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<PreferenceViewHolder> (
                    hasDescendant(withText(R.string.day_night_title)),
                    click()))

        val darkModeChanged = sharePreference.getBoolean(activityRule.activity.getString(R.string.day_night_key), false)
        assertEquals(!darkModeValue, darkModeChanged)
    }


    private fun launchFragment(destinationId: Int,
                                 argBundle: Bundle? = null) {
        val launchFragmentIntent = buildLaunchFragmentIntent(destinationId, argBundle)
        activityRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent =
        NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.main_nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .setArguments(argBundle)
            .createTaskStackBuilder().intents[0]
}