package com.print.locky.input

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput

suspend fun PointerInputScope.detectMultiplePointersInput(
    onTouchDown: (Int, Float, Float) -> Unit,
    onTouchMove: (Int, Float, Float) -> Unit,
    onTouchUp: (Int) -> Unit
) {
    forEachGesture {
        awaitPointerEventScope {
            val down = awaitFirstDown()
            onTouchDown(down.id.value.toInt(), down.position.x, down.position.y)

            do {
                val event = awaitPointerEvent()
                event.changes.forEach { change ->
                    if (change.position != change.previousPosition) {
                        onTouchMove(change.id.value.toInt(), change.position.x, change.position.y)
                    }
                    if (change.changedToUp()) {
                        onTouchUp(change.id.value.toInt())
                    }
                }
            } while (event.changes.any { it.pressed })
        }
    }
}
