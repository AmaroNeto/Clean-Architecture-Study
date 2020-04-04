package com.amaro.todolist.presentation.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.amaro.todolist.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openNewTodo() {
        onView(withId(R.id.newTodoButton)).perform(click());
        onView(withText(R.string.new_todo)).check(matches(isDisplayed()));
    }

    @Test
    fun openSettingsFragment() {
        openActionBarOverflowOrOptionsMenu(activityRule.activity);
        onView(withText(R.string.settings_title)).perform(click());
        onView(withText(R.string.settings_title)).check(matches(isDisplayed()));
    }
}