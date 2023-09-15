package com.example.petstore.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.petstore.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test

class DetailFragmentsAndroidTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun goToDetailFragment(){
        onView(ViewMatchers.withText("Cama Deluxe")).perform(click())
    }

}