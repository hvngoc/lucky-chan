package com.print.locky

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class GameScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Disable animations to prevent IdlingResourceTimeoutException
        composeTestRule.mainClock.autoAdvance = false
    }

    @Test
    fun getReadyText_isDisplayedOnStart() {
        // Wait for composition to settle
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Get ready").assertIsDisplayed()
    }

    @Test
    fun appTitle_isDisplayed() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Lucky Chan").assertIsDisplayed()
    }

    @Test
    fun darkModeToggle_changesTheme() {
        composeTestRule.waitForIdle()

        // Find the dark mode toggle button
        val darkModeIcon = composeTestRule.onAllNodesWithContentDescription("Dark Mode").onFirst()
        darkModeIcon.assertIsDisplayed()

        // Click to toggle to dark mode
        darkModeIcon.performClick()
        composeTestRule.waitForIdle()

        // After clicking, it should show "Light Mode" content description
        composeTestRule.onAllNodesWithContentDescription("Light Mode").onFirst().assertIsDisplayed()
    }

    @Test
    fun lightModeToggle_changesTheme() {
        composeTestRule.waitForIdle()

        // First switch to dark mode
        val darkModeIcon = composeTestRule.onAllNodesWithContentDescription("Dark Mode").onFirst()
        darkModeIcon.performClick()
        composeTestRule.waitForIdle()

        // Then switch back to light mode
        val lightModeIcon = composeTestRule.onAllNodesWithContentDescription("Light Mode").onFirst()
        lightModeIcon.assertIsDisplayed()
        lightModeIcon.performClick()
        composeTestRule.waitForIdle()

        // Should be back to dark mode toggle
        composeTestRule.onAllNodesWithContentDescription("Dark Mode").onFirst().assertIsDisplayed()
    }
}
