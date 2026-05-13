package com.app.habittracker.presentation.dashboard.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.presentation.common.GlobalTimerViewModel
import com.app.habittracker.ui.theme.getAppGlassColor
import com.app.habittracker.util.TimeUtils

@Composable
fun HabitCard(
    habit: HabitEntity,
    timerViewModel: GlobalTimerViewModel,
    onComplete: () -> Unit
) {
    val activeTitle by timerViewModel.activeTimerTitle.collectAsState()
    val timeLeft by timerViewModel.timerRemainingSeconds.collectAsState()
    val isPaused by timerViewModel.isPaused.collectAsState()
    val isThisTimerActive = activeTitle == habit.title

    val currentTime = remember {
        java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH)
            .format(java.util.Calendar.getInstance().time)
    }

    val start24 = TimeUtils.formatTo24h(habit.startTime)
    val end24 = TimeUtils.formatTo24h(habit.endTime)

    val isActive = currentTime >= start24 && currentTime <= end24
    val isUpcoming = currentTime < start24
    val isMissed = habit.isMissedToday || (currentTime > end24 && !habit.isCompletedToday)

    val categoryColor = when (habit.category) {
        "Health" -> Color(0xFF10B981)
        "Work" -> Color(0xFF3B82F6)
        "Social" -> Color(0xFFEC4899)
        "Mind" -> Color(0xFF8B5CF6)
        "Finance" -> Color(0xFFF59E0B)
        "Creative" -> Color(0xFF06B6D4)
        else -> MaterialTheme.colorScheme.primary
    }

    val cardBorderGradient = when {
        habit.isMastered -> listOf(Color(0xFFFFD700), Color(0xFFFFA000))
        habit.isCompletedToday -> listOf(Color(0xFF4CAF50), Color(0xFF81C784))
        isMissed -> listOf(Color(0xFFD32F2F), Color(0xFFE57373))
        isActive -> listOf(categoryColor, categoryColor.copy(alpha = 0.6f))
        else -> listOf(
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        )
    }

    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(getAppGlassColor())
            .border(
                width = if (isActive || habit.isCompletedToday) 2.dp else 1.dp,
                brush = Brush.linearGradient(cardBorderGradient),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = when {
                        habit.isCompletedToday -> if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFF4CAF50).copy(alpha = 0.2f) else Color(0xFFE8F5E9)
                        isMissed -> if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFFD32F2F).copy(alpha = 0.2f) else Color(0xFFFFEBEE)
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = habit.icon, style = MaterialTheme.typography.headlineMedium)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isMissed) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp), tint = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when {
                                habit.isMastered -> "MASTERED ✨"
                                habit.isCompletedToday -> "Completed! ✨"
                                isMissed -> "Missed Today ❌"
                                isUpcoming -> "Upcoming at ${habit.startTime} ⏳"
                                isActive -> if (isThisTimerActive) "Focusing... ⚡" else "Active Now! 🔥"
                                else -> "${habit.difficulty} • ${habit.streak} day streak"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (habit.isMastered) FontWeight.Bold else FontWeight.Normal,
                            color = if (habit.isMastered) Color(0xFFFFA000) else if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }

                Box(modifier = Modifier.size(52.dp), contentAlignment = Alignment.Center) {
                    if (isThisTimerActive && !habit.isCompletedToday) {
                        val totalSeconds = habit.targetDuration * 60
                        val progress = if (totalSeconds > 0) timeLeft.toFloat() / totalSeconds.toFloat() else 0f
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawArc(color = categoryColor.copy(alpha = 0.2f), startAngle = -90f, sweepAngle = 360f, useCenter = false, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4.dp.toPx()))
                            drawArc(color = categoryColor, startAngle = -90f, sweepAngle = 360f * progress, useCenter = false, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4.dp.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round))
                        }
                    }
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                            .background(Brush.linearGradient(if (habit.isCompletedToday || !isActive) listOf(Color.Gray, Color.LightGray) else listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)))
                            .clickable(enabled = isActive && !habit.isCompletedToday) {
                                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                onComplete()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Complete", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
            }
            if (isThisTimerActive && !habit.isCompletedToday) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = String.format(java.util.Locale.getDefault(), "%02d:%02d remaining", timeLeft / 60, timeLeft % 60), style = MaterialTheme.typography.labelSmall, color = categoryColor, fontWeight = FontWeight.Bold)
                    if (isPaused) Text("PAUSED", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun TimerDialog(
    habit: HabitEntity,
    viewModel: GlobalTimerViewModel,
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    val activeTitle by viewModel.activeTimerTitle.collectAsState()
    val timeLeft by viewModel.timerRemainingSeconds.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()

    LaunchedEffect(Unit) { if (activeTitle != habit.title) viewModel.startTimer(habit.title, habit.targetDuration) }
    LaunchedEffect(timeLeft) { if (activeTitle == habit.title && timeLeft == 0) onComplete() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Focus: ${habit.title}", fontWeight = FontWeight.Bold) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(text = String.format(java.util.Locale.getDefault(), "%02d:%02d", timeLeft / 60, timeLeft % 60), style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.ExtraBold, color = if (isPaused) Color.Gray else MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { if (isPaused) viewModel.resumeExistingTimer() else viewModel.pauseTimer() }, colors = ButtonDefaults.buttonColors(containerColor = if (isPaused) Color(0xFF4CAF50) else Color(0xFFFF9800)), modifier = Modifier.fillMaxWidth()) {
                    Text(if (isPaused) "Resume Session" else "Pause Session")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Your progress is saved automatically. You can leave this page!", textAlign = TextAlign.Center)
            }
        },
        confirmButton = { Button(onClick = onDismiss) { Text("Keep Running") } },
        dismissButton = { TextButton(onClick = { viewModel.stopTimer(); onDismiss() }) { Text("Stop Timer") } }
    )
}
