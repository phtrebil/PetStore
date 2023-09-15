package com.example.petstore.ui

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.petstore.R
import com.example.petstore.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test

class HomeFragmentsAndroidTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun cleanShoppingCart() {
        onView(withId(R.id.carrinho)).perform(click())
        onView(withText("Sim")).perform(click())
    }

    @Test
    fun filterByCategory() {
        onView(withId(R.id.camas)).perform(click())
    }

    @Test
    fun filterByName() {
        onView(withId(R.id.searchView)).perform(
            click(),
            typeText("Cama Deluxe"),
            pressKey(KeyEvent.KEYCODE_ENTER),
            closeSoftKeyboard()
        )
    }

    @Test
    fun homeScreenTotalTest(){
        cleanShoppingCart()
        filterByCategory()
        filterByName()
    }

}