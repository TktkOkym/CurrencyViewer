package com.tktkokym.currencyviewer.view.fragment

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.tktkokym.currencyviewer.R
import com.tktkokym.currencyviewer.view.activity.HomeActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun jumpToCurrencyViewerFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.home_navigation_fragment).navigate(R.id.fragment_currency_viewer)
            }
        }
    }

    @Test
    fun editTextInputTest() {
        val editTextView = onView(withId(R.id.currency_input_edit_text))
        editTextView.perform(ViewActions.replaceText(EDIT_TEXT_INPUT), ViewActions.closeSoftKeyboard())
        editTextView.check(matches(ViewMatchers.withText(EDIT_TEXT_INPUT)))
    }

    @Test
    fun visibilityTest() {
        onView(withId(R.id.currency_input_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_list_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_select_spinner)).check(matches(isDisplayed()))
    }

    companion object {
        const val EDIT_TEXT_INPUT = "100"
    }
}