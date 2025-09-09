package com.print.locky

import kotlin.test.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {
    @Test
    fun testAddTouchPoint() = runTest {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        assertEquals(1, vm.touchPoints.value.size)
        assertEquals(1, vm.touchPoints.value[0].number)
    }

    @Test
    fun testRemoveTouchPoint() = runTest {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        vm.onTouchUp(1)
        assertEquals(0, vm.touchPoints.value.size)
    }

    @Test
    fun testResetGame() = runTest {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        vm.resetGame()
        assertEquals(0, vm.touchPoints.value.size)
        assertNull(vm.selectedPoint.value)
        assertNull(vm.countdown.value)
    }

    @Test
    fun testCountdownStartsAndSelectsWinner() = runTest {
        val vm = GameViewModel()
        vm.onTouchDown(1, 100f, 200f)
        vm.onTouchDown(2, 200f, 300f)
        // Simulate inactivity to trigger countdown
        vm.onTouchUp(3) // no effect, just to simulate
        // Start countdown manually for test
        val countdowns = mutableListOf<Int?>()
        val job = kotlinx.coroutines.launch {
            vm.countdown.take(5).toList(countdowns)
        }
        vm.javaClass.getDeclaredMethod("startCountdown").apply { isAccessible = true }.invoke(vm)
        job.cancel()
        assertTrue(countdowns.contains(5))
        // After countdown, a winner should be selected
        vm.javaClass.getDeclaredMethod("selectWinner").apply { isAccessible = true }.invoke(vm)
        assertNotNull(vm.selectedPoint.value)
        assertTrue(vm.touchPoints.value.contains(vm.selectedPoint.value))
    }
}

