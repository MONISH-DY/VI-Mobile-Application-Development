package com.app.habittracker.presentation.dashboard.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.habittracker.data.local.entities.AchievementEntity
import com.app.habittracker.data.local.entities.HabitEntity

@Composable
fun DashboardPopups(
    xpEarned: Int?,
    levelUp: Int?,
    levelDown: Int?,
    achievement: AchievementEntity?,
    evolvingHabit: HabitEntity?,
    isComboActive: Boolean,
    lootMessage: String?,
    onDismissLoot: () -> Unit,
    onDismissEvolution: () -> Unit,
    onDismissLevelUp: () -> Unit,
    onDismissLevelDown: () -> Unit,
    onEvolveHabit: (HabitEntity) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // XP Earned Animation Popup
        AnimatedVisibility(
            visible = xpEarned != null,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it / 2 }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { -it }),
            modifier = Modifier.align(Alignment.Center)
        ) {
            xpEarned?.let { amount ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Text(
                        text = "+$amount XP",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Level Up Neon Animation Popup
        AnimatedVisibility(
            visible = levelUp != null,
            enter = scaleIn(animationSpec = tween(600, easing = FastOutSlowInEasing)) + fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(500)) + scaleOut(targetScale = 1.5f, animationSpec = tween(500)),
            modifier = Modifier.align(Alignment.Center)
        ) {
            levelUp?.let { level ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(color = MaterialTheme.colorScheme.surface)
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
                        .clickable { onDismissLevelUp() }
                        .padding(32.dp)
                ) {
                    Text(text = "🚀", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "LEVEL UP!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Level $level Reached!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Level Down Animation Popup
        AnimatedVisibility(
            visible = levelDown != null,
            enter = scaleIn(animationSpec = tween(600)) + fadeIn(),
            exit = fadeOut() + scaleOut(targetScale = 0.5f),
            modifier = Modifier.align(Alignment.Center)
        ) {
            levelDown?.let { level ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(color = MaterialTheme.colorScheme.errorContainer)
                        .border(2.dp, MaterialTheme.colorScheme.error, RoundedCornerShape(24.dp))
                        .clickable { onDismissLevelDown() }
                        .padding(32.dp)
                ) {
                    Text(text = "📉", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "LEVEL DOWN",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = "Back to Level $level",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }

        // Achievement Unlocked Popup
        AnimatedVisibility(
            visible = achievement != null,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp)
        ) {
            achievement?.let { badge ->
                Card(
                    modifier = Modifier.fillMaxWidth(0.85f).padding(8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape).background(color = Color.White.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = badge.icon, fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "ACHIEVEMENT UNLOCKED!",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFB8860B)
                            )
                            Text(
                                text = badge.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // Habit Evolution Popup
        AnimatedVisibility(
            visible = evolvingHabit != null,
            enter = fadeIn() + scaleIn(initialScale = 0.8f),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            evolvingHabit?.let { habit ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .clip(RoundedCornerShape(32.dp))
                        .background(brush = Brush.verticalGradient(listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2))))
                        .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(32.dp))
                        .padding(24.dp)
                ) {
                    Text(text = "✨", fontSize = 48.sp)
                    Text(
                        text = "EVOLUTION",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mastery Reached: ${habit.title}",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TextButton(onClick = onDismissEvolution) {
                            Text("Later", color = Color.White.copy(alpha = 0.7f))
                        }
                        Button(
                            onClick = { onEvolveHabit(habit) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
                        ) {
                            Text("Upgrade", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // COMBO Popup
        AnimatedVisibility(
            visible = isComboActive,
            enter = scaleIn(animationSpec = tween(300)),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                "COMBO! ⚡ 1.5x XP",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFFD700),
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    .padding(16.dp)
            )
        }

        // LOOT Popup
        AnimatedVisibility(
            visible = lootMessage != null,
            enter = scaleIn(animationSpec = tween(800)) + fadeIn(),
            exit = fadeOut() + scaleOut(targetScale = 0.5f),
            modifier = Modifier.align(Alignment.Center)
        ) {
            lootMessage?.let { msg ->
                Box(modifier = Modifier.fillMaxSize().clickable { onDismissLoot() }) {
                    LootBoxOverlay(message = msg)
                }
            }
        }
    }
}

@Composable
fun LootBoxOverlay(message: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "rays")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "raysAnim"
    )

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(400.dp).graphicsLayer { rotationZ = rotation }
                .background(Brush.radialGradient(
                    colors = listOf(Color(0xFFFFD700).copy(alpha = 0.3f), Color.Transparent),
                    center = androidx.compose.ui.geometry.Offset(200f, 200f),
                    radius = 400f
                ))
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Text(text = when {
                message.contains("XP_BOOSTER") -> "⚡"
                message.contains("RARE_SKIN") -> "🎨"
                else -> "🎁"
            }, fontSize = 100.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "LOOT UNLOCKED!", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Black, color = Color(0xFFFFD700))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = when {
                message.contains("XP_BOOSTER") -> "XP Booster Active! (2x XP for 1 Hour)"
                message.contains("RARE_SKIN") -> "New Sparky Skin Unlocked: ${message.substringAfter(":")}!"
                message.contains("MEGA_XP") -> "Mega XP! (+500 XP Awarded)"
                else -> message
            }, style = MaterialTheme.typography.headlineSmall, color = Color.White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "(Click anywhere to continue)", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.6f))
        }
    }
}
