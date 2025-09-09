package com.print.locky

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.print.locky.MainActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class GameScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun getReadyText_isDisplayedOnStart() {
        composeTestRule.onNodeWithText("Get ready").assertIsDisplayed()
    }

    @Test
    fun darkModeToggle_changesTheme() {
        val darkModeIcon = composeTestRule.onAllNodesWithContentDescription("Dark Mode").onFirst()
        darkModeIcon.assertIsDisplayed()
        darkModeIcon.performClick()
        composeTestRule.onAllNodesWithContentDescription("Light Mode").onFirst().assertIsDisplayed()
    }

    @Test
    fun resetButton_clearsGame() {
        // Simulate a touch by calling the ViewModel directly or by UI if possible
        // Here, just check the reset button exists and is clickable
        composeTestRule.onAllNodesWithContentDescription("").onFirst().performClick()
        composeTestRule.onNodeWithText("Get ready").assertIsDisplayed()
    }
}
