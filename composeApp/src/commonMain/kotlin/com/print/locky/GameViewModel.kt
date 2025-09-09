package com.print.locky

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.sqrt
import kotlin.random.Random

data class TouchPoint(
    val id: Int,
    val x: Float,
    val y: Float,
    val number: Int,
    val color: Int = Random.nextInt()
)

class GameViewModel {
    private val scope = CoroutineScope(Dispatchers.Main)
    private var countdownJob: Job? = null
    private var inactivityJob: Job? = null

    private val _touchPoints = mutableStateOf<List<TouchPoint>>(emptyList())
    val touchPoints: State<List<TouchPoint>> = _touchPoints

    private val _countdown = MutableStateFlow<Int?>(null)
    val countdown: StateFlow<Int?> = _countdown

    private val _selectedPoint = mutableStateOf<TouchPoint?>(null)
    val selectedPoint: State<TouchPoint?> = _selectedPoint

    private var nextNumber = 1

    private fun isPointTooClose(x: Float, y: Float): Boolean {
        val minDistance = 50.dp.value
        return _touchPoints.value.any { point ->
            val dx = x - point.x
            val dy = y - point.y
            sqrt(dx * dx + dy * dy) < minDistance
        }
    }

    fun onTouchDown(id: Int, x: Float, y: Float) {
        if (_selectedPoint.value == null && !isPointTooClose(x, y)) {
            _touchPoints.value = _touchPoints.value + TouchPoint(id, x, y, nextNumber++)
            resetInactivityTimer()
        }
    }

    fun onTouchMove(id: Int, x: Float, y: Float) {
        if (_selectedPoint.value == null) {
            _touchPoints.value = _touchPoints.value.map {
                if (it.id == id) it.copy(x = x, y = y) else it
            }
        }
    }

    fun onTouchUp(id: Int) {
        if (_selectedPoint.value == null) {
            _touchPoints.value = _touchPoints.value.filter { it.id != id }
            if (_touchPoints.value.isNotEmpty()) {
                resetInactivityTimer()
            } else {
                resetGame()
            }
        }
    }

    private fun resetInactivityTimer() {
        inactivityJob?.cancel()
        _countdown.value = null
        _selectedPoint.value = null

        if (_touchPoints.value.isNotEmpty()) {
            inactivityJob = scope.launch {
                delay(3000) // Wait 3 seconds of inactivity
                startCountdown()
            }
        }
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = scope.launch {
            for (i in 5 downTo 1) {
                _countdown.value = i
                delay(1000)
            }
            _countdown.value = null
            selectWinner()
        }
    }

    private fun selectWinner() {
        if (_touchPoints.value.isNotEmpty()) {
            _selectedPoint.value = _touchPoints.value.random()
        }
    }

    fun resetGame() {
        countdownJob?.cancel()
        inactivityJob?.cancel()
        _countdown.value = null
        nextNumber = 1
        _touchPoints.value = emptyList()
        _selectedPoint.value = null
    }
}
