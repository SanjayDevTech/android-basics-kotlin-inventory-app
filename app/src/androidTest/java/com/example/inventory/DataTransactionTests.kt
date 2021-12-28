package com.example.inventory

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataTransactionTests {

    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        onView(withId(R.id.clear_action)).perform(click())
        onView(withId(R.id.recyclerView))
            .check(matches(hasChildCount(0)))
    }

    private val testItemName = "Item 1"
    private val testItemPrice = "5.00"
    private val testItemCount = "10"
    private val testItemNameEdited = "Item 2"
    private val testItemPriceEdited = "10.00"
    private val testItemCountEdited = "5"
    private val quantityHeader = R.string.quantity_in_stock

    @Test
    fun addItemAndCheckInList() {
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.item_name)).perform(typeText(testItemName))
        onView(withId(R.id.item_price)).perform(typeText(testItemPrice))
        onView(withId(R.id.item_count)).perform(typeText(testItemCount))
        onView(withId(R.id.save_action)).perform(click())

        onView(withText(quantityHeader)).check(matches(isDisplayed()))

        onView(withText(testItemName)).check(matches(isDisplayed()))
        onView(withText("$$testItemPrice")).check(matches(isDisplayed()))
        onView(withText(testItemCount)).check(matches(isDisplayed()))
    }

    @Test
    fun sellItemAndCheck() {
        addItemAndCheckInList()
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ItemListAdapter.ItemViewHolder>(0, click())
        )
        onView(withId(R.id.item_name)).check(matches(withText(testItemName)))
        onView(withId(R.id.item_price)).check(matches(withText("$$testItemPrice")))
        onView(withId(R.id.item_count)).check(matches(withText(testItemCount)))

        onView(withId(R.id.sell_item)).perform(click())
        onView(withId(R.id.item_count)).check(matches(withText("9")))

        onView(withId(R.id.sell_item)).perform(click())
        onView(withId(R.id.item_count)).check(matches(withText("8")))

        onView(withId(R.id.sell_item)).perform(click())
        onView(withId(R.id.item_count)).check(matches(withText("7")))

        onView(isRoot()).perform(
            pressBack())

        onView(withText(quantityHeader)).check(matches(isDisplayed()))

        onView(withText(testItemName)).check(matches(isDisplayed()))
        onView(withText("$$testItemPrice")).check(matches(isDisplayed()))
        onView(withText("7")).check(matches(isDisplayed()))
    }

    @Test
    fun editItemAndCheckInList() {
        addItemAndCheckInList()

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ItemListAdapter.ItemViewHolder>(0, click())
        )
        onView(withId(R.id.item_name)).check(matches(withText(testItemName)))
        onView(withId(R.id.item_price)).check(matches(withText("$$testItemPrice")))
        onView(withId(R.id.item_count)).check(matches(withText(testItemCount)))

        onView(withId(R.id.edit_item)).perform(click())

        onView(withId(R.id.item_name)).perform(clearText())
        onView(withId(R.id.item_price)).perform(clearText())
        onView(withId(R.id.item_count)).perform(clearText())

        onView(withId(R.id.item_name)).perform(typeText(testItemNameEdited))
        onView(withId(R.id.item_price)).perform(typeText(testItemPriceEdited))
        onView(withId(R.id.item_count)).perform(typeText(testItemCountEdited))
        onView(withId(R.id.save_action)).perform(click())

        onView(withText(quantityHeader)).check(matches(isDisplayed()))

        onView(withText(testItemNameEdited)).check(matches(isDisplayed()))
        onView(withText("$$testItemPriceEdited")).check(matches(isDisplayed()))
        onView(withText(testItemCountEdited)).check(matches(isDisplayed()))

    }

}