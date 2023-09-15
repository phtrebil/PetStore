package com.example.petstore.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.petstore.R
import com.example.petstore.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test

class DetailFragmentsAndroidTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun goToDetailFragment(){
        onView(withText("Cama Deluxe")).perform(click())
    }

    @Test
    fun incrementButtonTest(){
        goToDetailFragment()

        val clicks = 10
        for (i in 0 until clicks){
            onView(withId(R.id.incrementButton)).perform(click())

        }
    }

    @Test
    fun decrementButtonTest(){
        goToDetailFragment()

        val clicks = 8
        for (i in 0 until clicks){
            onView(withId(R.id.decrementButton)).perform(click())

        }
    }

    @Test
    fun buyingButton(){
        goToDetailFragment()
        onView(withId(R.id.addButton)).perform(click())
    }

    @Test
    fun detailScreenTotalTest(){
        goToDetailFragment()
        incrementButtonTest()
        decrementButtonTest()
        buyingButton()
    }

}