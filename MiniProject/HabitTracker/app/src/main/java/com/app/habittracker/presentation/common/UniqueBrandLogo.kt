package com.app.habittracker.presentation.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun UniqueBrandLogo(size: Dp = 120.dp) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo")
    
    // Smooth organic pulse
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Rotation for the energy trails
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(modifier = Modifier.size(size)) {
        val radius = (size.toPx() / 2) * pulse
        val center = center

        // Outer Ethereal Glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF00FFCC).copy(alpha = 0.3f), Color.Transparent),
                center = center,
                radius = radius * 1.5f
            ),
            radius = radius * 1.5f
        )

        // 8-Pointed Star Crest Core
        val path = Path().apply {
            val innerRadius = radius * 0.4f
            val outerRadius = radius
            for (i in 0 until 16) {
                val angle = Math.toRadians((i * 22.5).toDouble())
                val r = if (i % 2 == 0) outerRadius else innerRadius
                val x = center.x + r * kotlin.math.cos(angle).toFloat()
                val y = center.y + r * kotlin.math.sin(angle).toFloat()
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }

        drawPath(
            path = path,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFFFD700), Color(0xFF00FFCC)) // Gold to Teal
            )
        )

        // Swirling Energy Trails
        for (i in 0..2) {
            val offsetRotation = rotation + (i * 120f)
            drawCircle(
                color = Color.White.copy(alpha = 0.5f),
                radius = 8f,
                center = center.copy(
                    x = center.x + (radius * 0.6f) * kotlin.math.cos(Math.toRadians(offsetRotation.toDouble())).toFloat(),
                    y = center.y + (radius * 0.6f) * kotlin.math.sin(Math.toRadians(offsetRotation.toDouble())).toFloat()
                )
            )
        }
    }
}
