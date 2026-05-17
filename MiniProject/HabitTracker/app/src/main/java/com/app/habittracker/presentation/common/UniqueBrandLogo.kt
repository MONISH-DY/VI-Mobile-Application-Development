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

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.app.habittracker.R

@Composable
fun UniqueBrandLogo(size: Dp = 120.dp) {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "HabitPulse Logo",
        modifier = Modifier.size(size)
    )
}
