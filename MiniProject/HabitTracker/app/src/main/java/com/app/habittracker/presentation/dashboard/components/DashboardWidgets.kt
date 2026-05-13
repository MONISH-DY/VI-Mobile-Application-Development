package com.app.habittracker.presentation.dashboard.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.habittracker.data.local.entities.BossBattleEntity
import com.app.habittracker.data.local.entities.QuestEntity
import com.app.habittracker.data.local.entities.UserEntity
import com.app.habittracker.ui.theme.BossGradient
import com.app.habittracker.ui.theme.PrimaryGradient
import com.app.habittracker.ui.theme.QuestGradient

@Composable
fun SparkyAvatar(level: Int, skin: String = "DEFAULT") {
    val infiniteTransition = rememberInfiniteTransition(label = "sparky")
    val glowSize by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val sparkyColor = when (skin) {
        "CYAN" -> Color(0xFF00FFCC)
        "PINK" -> Color(0xFFE91E63)
        "GOLD" -> Color(0xFFFFD700)
        "RAINBOW" -> {
            val hue by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
                label = "hue"
            )
            Color.hsv(hue, 0.7f, 0.9f)
        }
        else -> when {
            level < 5 -> Color(0xFFFFFFFF)
            level < 15 -> Color(0xFF00FFCC)
            level < 30 -> Color(0xFFE91E63)
            else -> Color(0xFFFFD700)
        }
    }

    Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(sparkyColor.copy(alpha = 0.6f), Color.Transparent),
                    center = center,
                    radius = glowSize.dp.toPx()
                ),
                radius = glowSize.dp.toPx()
            )
        }
        Surface(modifier = Modifier.size(24.dp), shape = CircleShape, color = sparkyColor, tonalElevation = 8.dp, shadowElevation = (glowSize / 2).dp) {}
        if (level >= 10) {
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
                label = "orbit"
            )
            Box(modifier = Modifier.fillMaxSize().graphicsLayer { rotationZ = rotation }) {
                Surface(modifier = Modifier.size(8.dp).align(Alignment.TopCenter), shape = CircleShape, color = sparkyColor.copy(alpha = 0.8f)) {}
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
fun UserStatsSection(user: UserEntity) {
    Card(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Brush.linearGradient(PrimaryGradient)).padding(20.dp)) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(text = "Level ${user.currentLevel}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text(text = "Rank: Master Tracker", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
                    }
                    SparkyAvatar(level = user.currentLevel, skin = user.currentSparkySkin)
                }
                Spacer(modifier = Modifier.height(24.dp))
                val xpProgress = (user.totalXp % 100) / 100f
                val animatedProgress by animateFloatAsState(targetValue = xpProgress, animationSpec = tween(1000), label = "xp")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "XP Progress: ${user.totalXp % 100}%", style = MaterialTheme.typography.labelMedium, color = Color.White, fontWeight = FontWeight.SemiBold)
                    Text(text = "Next: Level ${user.currentLevel + 1}", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.9f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier.fillMaxWidth().height(14.dp).clip(RoundedCornerShape(7.dp)),
                    color = Color(0xFF0F172A),
                    trackColor = Color.White.copy(alpha = 0.2f),
                )
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
fun DashboardWidgetsRow(
    quests: List<QuestEntity>,
    boss: BossBattleEntity?,
    onNavigateToQuests: () -> Unit,
    onNavigateToBossBattle: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Quests Widget
        Card(
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).clickable { onNavigateToQuests() },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(QuestGradient)).padding(20.dp)) {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                    Text("🎯 Quests", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
                    val completedQuests = quests.count { it.isCompleted }
                    val totalQuests = if (quests.isEmpty()) 3 else quests.size
                    Column {
                        Text(text = "$completedQuests / $totalQuests", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        LinearProgressIndicator(
                            progress = completedQuests.toFloat() / totalQuests.toFloat(),
                            modifier = Modifier.fillMaxWidth().height(8.dp).padding(vertical = 1.dp).clip(CircleShape),
                            color = Color(0xFF0F172A),
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }

        // Boss Widget
        Card(
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).clickable { onNavigateToBossBattle() },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(BossGradient)).padding(20.dp)) {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("👹 Boss", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
                        if (boss?.isEnraged == true) {
                            Text(
                                "ENRAGED",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = Color.Red,
                                modifier = Modifier.background(Color.White, RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (boss != null && !boss.isDefeated) {
                        val percent = (boss.currentHp.toFloat() / boss.maxHp.toFloat()) * 100
                        Column {
                            Text(text = "${percent.toInt()}% HP", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            LinearProgressIndicator(
                                progress = boss.currentHp.toFloat() / boss.maxHp.toFloat(),
                                modifier = Modifier.fillMaxWidth().height(8.dp).padding(vertical = 1.dp).clip(CircleShape),
                                color = Color(0xFF0F172A),
                                trackColor = Color.White.copy(alpha = 0.3f)
                            )
                        }
                    } else {
                        Text(text = if (boss?.isDefeated == true) "Defeated!" else "No Boss", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "Tap to fight", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.8f))
                    }
                }
            }
        }
    }
}
