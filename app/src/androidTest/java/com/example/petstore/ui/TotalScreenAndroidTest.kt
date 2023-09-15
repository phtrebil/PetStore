package com.example.petstore.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.petstore.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test

class TotalScreenAndroidTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun totalScreenAndroidTest(){
        DetailFragmentsAndroidTest().detailScreenTotalTest()
        HomeFragmentsAndroidTest().homeScreenTotalTest()
    }
}