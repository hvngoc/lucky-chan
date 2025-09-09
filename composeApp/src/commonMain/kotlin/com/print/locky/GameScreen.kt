package com.print.locky

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.material3.Text as M3Text

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun GameScreen() {
    val viewModel = remember { GameViewModel() }

    val touchPoints by viewModel.touchPoints
    val selectedPoint by viewModel.selectedPoint
    val countdown by viewModel.countdown.collectAsState(initial = null)

    val textMeasurer = rememberTextMeasurer()
    val haptic = LocalHapticFeedback.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { M3Text("Locky") },
                actions = {
                    Icon(imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .clickable { viewModel.resetGame() })
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green
                )
            )
            var showGetReady by remember { mutableStateOf(true) }
            // Hide 'get ready' when at least 1 point is touched
            LaunchedEffect(touchPoints.size) {
                showGetReady = touchPoints.isEmpty()
            }
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                // Show 'get ready' if no points
                if (showGetReady) {
                    var scale by remember { mutableStateOf(1f) }
                    LaunchedEffect(Unit) {
                        while (showGetReady) {
                            animate(
                                initialValue = 1f,
                                targetValue = 1.3f,
                                animationSpec = tween(durationMillis = 600)
                            ) { value, _ ->
                                scale = value
                            }
                            animate(
                                initialValue = 1.3f,
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = 600)
                            ) { value, _ ->
                                scale = value
                            }
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        M3Text(
                            text = "Get ready",
                            modifier = Modifier.graphicsLayer(
                                scaleX = scale,
                                scaleY = scale
                            ),
                            style = TextStyle(
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                // Touch detection area
                if (selectedPoint == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        event.changes.forEach { change ->
                                            when {
                                                change.pressed -> {
                                                    val x = change.position.x
                                                    val y = change.position.y
                                                    if (touchPoints.any { point ->
                                                            val dx = x - point.x
                                                            val dy = y - point.y
                                                            kotlin.math.sqrt(dx * dx + dy * dy) < 50.dp.toPx()
                                                        }) {
                                                        // Touch is too close to another point
                                                    } else {
                                                        viewModel.onTouchDown(
                                                            change.id.value.toInt(),
                                                            x,
                                                            y
                                                        )
                                                        haptic.performHapticFeedback(
                                                            HapticFeedbackType.LongPress
                                                        )
                                                    }
                                                }

                                                change.position != change.previousPosition -> {
                                                    viewModel.onTouchMove(
                                                        change.id.value.toInt(),
                                                        change.position.x,
                                                        change.position.y
                                                    )
                                                }

                                                !change.pressed -> {
                                                    viewModel.onTouchUp(change.id.value.toInt())
                                                }
                                            }
                                            change.consume()
                                        }
                                    }
                                }
                            }
                    )
                }

                // Draw touch points and animations
                touchPoints.forEach { point ->
                    if (selectedPoint == null || point == selectedPoint) {
                        AnimatedTouchPoint(
                            point = point,
                            isSelected = point == selectedPoint,
                            textMeasurer = textMeasurer
                        )
                    }
                }

                // Countdown text
                countdown?.let { count: Int ->
                    var scale by remember { mutableStateOf(1.5f) }
                    LaunchedEffect(count) {
                        scale = 1.5f
                        animate(
                            initialValue = 1.5f,
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = 900)
                        ) { value, _ ->
                            scale = value
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        M3Text(
                            text = count.toString(),
                            modifier = Modifier.graphicsLayer(
                                scaleX = scale,
                                scaleY = scale
                            ),
                            style = TextStyle(
                                fontSize = 180.sp, // Make it bigger
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedTouchPoint(
    point: TouchPoint,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        )
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val baseRadius = 50.dp.toPx()
        val animatedRadius = baseRadius * (1f + sin(pulseAnim * 2 * PI.toFloat()) * 0.2f) * scale

        // Draw pulsing circle
        val pointColor = Color(
            red = Random.nextFloat() * 0.5f + 0.5f,
            green = Random.nextFloat() * 0.5f + 0.5f,
            blue = Random.nextFloat() * 0.5f + 0.5f,
            alpha = 0.6f
        )

        drawCircle(
            color = pointColor,
            radius = animatedRadius,
            center = Offset(point.x, point.y)
        )

        // Draw number background
        drawCircle(
            color = Color.White,
            radius = 20.dp.toPx() * scale,
            center = Offset(point.x, point.y)
        )

        // Draw number
        val textStyle = TextStyle(
            fontSize = (16 * scale).sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        val textLayoutResult = textMeasurer.measure(point.number.toString(), textStyle)
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                point.x - textLayoutResult.size.width / 2,
                point.y - textLayoutResult.size.height / 2
            )
        )
    }
}
