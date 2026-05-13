package com.app.habittracker.presentation.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun AtmosphereBackground(content: @Composable () -> Unit) {
    val isDark = isSystemInDarkTheme()
    
    // Minimal random seed for consistency in a single screen session
    val stars = remember {
        List(50) {
            Offset(Random.nextFloat(), Random.nextFloat()) to Random.nextFloat()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            
            stars.forEachIndexed { index, (pos, speedMultiplier) ->
                val x = pos.x * width
                val y = pos.y * height
                
                // More dynamic sparkling alpha
                val phase = (alphaAnim * speedMultiplier * 2f).coerceIn(0.1f, 1f)
                
                val color = if (isDark) {
                    // Radiant white stars
                    Color.White.copy(alpha = phase * 0.3f)
                } else {
                    // Shimmering gold sun sparkles
                    Color(0xFFFFD700).copy(alpha = phase * 0.3f)
                }
                
                // Varying sizes for more "glitter" look
                val baseRadius = if (isDark) 1.5.dp.toPx() else 2.dp.toPx()
                val finalRadius = baseRadius * (0.8f + (speedMultiplier * 0.4f))
                
                drawCircle(
                    color = color,
                    radius = finalRadius,
                    center = Offset(x, y)
                )
            }
        }
        content()
    }
}
