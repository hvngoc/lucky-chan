package com.print.locky

import kotlin.test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testAddTouchPoint() {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        assertEquals(1, vm.touchPoints.value.size)
        assertEquals(1, vm.touchPoints.value[0].number)
    }

    @Test
    fun testRemoveTouchPoint() {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        vm.onTouchUp(1)
        assertEquals(0, vm.touchPoints.value.size)
    }

    @Test
    fun testResetGame() {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        vm.resetGame()
        assertEquals(0, vm.touchPoints.value.size)
        assertNull(vm.selectedPoint.value)
        assertNull(vm.countdown.value)
    }

    @Test
    fun testSelectWinner() {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        vm.onTouchDown(2, 200f, 300f)

        // Test that selectWinner picks one of the available points
        vm.selectWinner()
        assertNotNull(vm.selectedPoint.value)
        assertTrue(vm.touchPoints.value.contains(vm.selectedPoint.value))
    }
}
