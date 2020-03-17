package com.example.ccctest.ui.login


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.ccctest.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var stringToBetyped: String

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "test@yopmail.com"
    }

    @Test
    @Throws(Exception::class)
    fun clickLoginButton() {
        onView(withId(R.id.btnLogin)).perform(click())
    }

    @Test
    fun changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.etEmail))
            .perform(typeText(stringToBetyped), closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())

        // Check that the text was changed.
        onView(withId(R.id.etEmail)).check(matches(withText(stringToBetyped)))
    }

}