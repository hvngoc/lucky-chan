package com.print.locky.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset

class WinnerAnimationState {
    val scale = Animatable(1f)
    val position = Animatable(0f)

    suspend fun playWinAnimation(
        startPosition: Offset,
        targetPosition: Offset
    ) {
        scale.animateTo(
            targetValue = 2f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }
}

@Composable
fun rememberWinnerAnimationState(): WinnerAnimationState {
    return remember { WinnerAnimationState() }
}
