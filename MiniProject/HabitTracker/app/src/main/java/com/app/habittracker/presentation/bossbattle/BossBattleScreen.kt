package com.app.habittracker.presentation.bossbattle

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.data.local.entities.BossBattleEntity
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BossBattleScreen(
    onNavigateBack: () -> Unit,
    viewModel: BossBattleViewModel = hiltViewModel(),
    timerViewModel: com.app.habittracker.presentation.common.GlobalTimerViewModel = hiltViewModel()
) {
    val boss by viewModel.currentBoss.collectAsState()

    // Removed unused background variable

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Weekly Boss",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        com.app.habittracker.presentation.common.AtmosphereBackground {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding),
                contentAlignment = Alignment.TopCenter
            ) {
                if (boss == null || (boss!!.isDefeated && System.currentTimeMillis() > boss!!.endDate)) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text("🛡️", fontSize = 80.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Peace Prevails",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No boss active today. Complete habits to keep the realm safe!",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    BossArena(
                        boss = boss!!,
                        timerViewModel = timerViewModel,
                        onTaskComplete = { viewModel.completeBossTask(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun BossArena(
    boss: BossBattleEntity,
    timerViewModel: com.app.habittracker.presentation.common.GlobalTimerViewModel,
    onTaskComplete: (Int) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        item {
            // Floating animation for boss
            val infiniteTransition = rememberInfiniteTransition(label = "float")
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = if (boss.isDefeated) 0f else -20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "floatAnim"
            )

            // Boss Image with Shake Animation


            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = if (boss.currentHp % 100 != 0 || boss.isEnraged) 5.dp else 0.dp, y = offsetY.dp),
                contentAlignment = Alignment.Center
            ) {
                if (boss.isEnraged && !boss.isDefeated) {
                    // Enrage Glow
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(Color.Red.copy(alpha = 0.4f), Color.Transparent),
                                radius = size.minDimension / 1.5f
                            )
                        )
                    }
                }
                Text(
                    text = if (boss.isDefeated) "👻" else if (boss.isEnraged) "👹🔥" else "👹",
                    fontSize = 120.sp
                )
            }

            if (boss.isEnraged && !boss.isDefeated) {
                Text(
                    text = "BOSS IS ENRAGED! DOUBLE PENALTIES ACTIVE!",
                    color = Color.Red,
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = boss.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = boss.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        if (boss.isDefeated) {
            item {
                val party = Party(
                    speed = 0f,
                    maxSpeed = 30f,
                    damping = 0.9f,
                    spread = 360,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    position = Position.Relative(0.5, 0.5),
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
                )
                Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                    KonfettiView(
                        parties = listOf(party),
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = "VICTORY!",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color(0xFFFFD700),
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Earned ${boss.rewardXp} XP\nUnlocked Boss Slayer Badge!",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White, // Keep white for gold victory background
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else if (System.currentTimeMillis() > boss.endDate) {
            item {
                // Failed State
                Text(
                    text = "BOSS ESCAPED!",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        } else {
            item {
                Text(
                    text = "⚔️ CHALLENGE TASKS",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 2.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
            item {
                // Task 1
                BossTaskCard(
                    taskName = boss.task1Name,
                    taskDesc = boss.task1Desc,
                    isCompleted = boss.task1Completed,
                    targetDuration = boss.task1Duration,
                    timerViewModel = timerViewModel,
                    onComplete = { onTaskComplete(1) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Task 2
                BossTaskCard(
                    taskName = boss.task2Name,
                    taskDesc = boss.task2Desc,
                    isCompleted = boss.task2Completed,
                    targetDuration = boss.task2Duration,
                    timerViewModel = timerViewModel,
                    onComplete = { onTaskComplete(2) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Task 3
                BossTaskCard(
                    taskName = boss.task3Name,
                    taskDesc = boss.task3Desc,
                    isCompleted = boss.task3Completed,
                    targetDuration = boss.task3Duration,
                    timerViewModel = timerViewModel,
                    onComplete = { onTaskComplete(3) }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun BossTaskCard(
    taskName: String,
    taskDesc: String,
    isCompleted: Boolean,
    targetDuration: Int = 0,
    timerViewModel: com.app.habittracker.presentation.common.GlobalTimerViewModel,
    onComplete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                androidx.compose.ui.graphics.Brush.linearGradient(
                    if (isCompleted) listOf(Color(0xFF00C853), Color(0xFF64DD17))
                    else com.app.habittracker.ui.theme.getSignatureGradient()
                )
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = taskName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = taskDesc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f) // Keep white for dark gradient card
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (isCompleted) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "TASK DONE",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            } else {
                var showTimer by remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        if (targetDuration > 0) showTimer = true
                        else onComplete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3333)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        if (targetDuration > 0) "Start Timer" else "Complete",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (showTimer) {
                    com.app.habittracker.presentation.dashboard.components.TimerDialog(
                        habit = com.app.habittracker.data.local.entities.HabitEntity(
                            title = taskName,
                            icon = "👿",
                            category = "Boss",
                            difficulty = "Hard",
                            frequency = "Weekly",
                            notes = taskDesc,
                            targetDuration = targetDuration,
                            preferredTime = ""
                        ),
                        viewModel = timerViewModel,
                        onDismiss = { showTimer = false },
                        onComplete = {
                            onComplete()
                            showTimer = false
                        }
                    )
                }
            }
        }
    }
}
