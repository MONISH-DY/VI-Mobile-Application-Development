package com.app.habittracker.presentation.quests

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.data.local.entities.QuestEntity
import com.app.habittracker.ui.theme.getSignatureGradient
import com.app.habittracker.ui.theme.getAppBackgroundGradient
import com.app.habittracker.ui.theme.getAppGlassColor
import com.app.habittracker.ui.theme.getAppBorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyQuestsScreen(
    onNavigateBack: () -> Unit,
    viewModel: DailyQuestsViewModel = hiltViewModel(),
    timerViewModel: com.app.habittracker.presentation.common.GlobalTimerViewModel = hiltViewModel()
) {
    val quests by viewModel.allQuests.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        com.app.habittracker.presentation.common.AtmosphereBackground {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Daily Quests",
                                fontWeight = FontWeight.Black,
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
                if (quests.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text("🎯", fontSize = 80.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No Quests Today",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Complete your regular habits to unlock bonus challenges!",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(quests) { quest ->
                            QuestCard(
                                quest = quest,
                                timerViewModel = timerViewModel,
                                onClaim = { viewModel.claimQuestReward(quest) },
                                onIncrement = { viewModel.incrementQuestProgress(quest) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestCard(
    quest: QuestEntity,
    timerViewModel: com.app.habittracker.presentation.common.GlobalTimerViewModel,
    onClaim: () -> Unit,
    onIncrement: () -> Unit
) {
    val isMissed = quest.isMissed
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (isMissed) 0.5f else 1f)
            .clip(RoundedCornerShape(16.dp))
            .background(com.app.habittracker.ui.theme.getAppGlassColor())
            .border(
                1.dp,
                com.app.habittracker.ui.theme.getAppBorderColor(),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isMissed) androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(
                                androidx.compose.ui.graphics.Color.DarkGray,
                                androidx.compose.ui.graphics.Color.DarkGray
                            )
                        )
                        else androidx.compose.ui.graphics.Brush.linearGradient(com.app.habittracker.ui.theme.getSignatureGradient())
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = if (isMissed) Color.White else MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quest.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = quest.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (quest.isIncremental) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = quest.currentProgress.toFloat() / quest.targetProgress.toFloat(),
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "${quest.currentProgress} / ${quest.targetProgress} units",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⏰ ${com.app.habittracker.util.TimeUtils.formatTo12h(quest.startTime)} - ${
                        com.app.habittracker.util.TimeUtils.formatTo12h(quest.endTime)
                    }",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (quest.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(36.dp).padding(end = 8.dp)
                )
            } else if (isMissed) {
                Text("MISSED", color = Color.Red, fontWeight = FontWeight.Bold)
            } else {
                var showTimer by remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        if (quest.isIncremental) onIncrement()
                        else if (quest.targetDuration > 0) showTimer = true
                        else onClaim()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (quest.isIncremental) "+1" else if (quest.targetDuration > 0) "Start" else "Claim",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                if (showTimer) {
                    com.app.habittracker.presentation.dashboard.components.TimerDialog(
                        habit = com.app.habittracker.data.local.entities.HabitEntity(
                            title = quest.title,
                            icon = "🎯",
                            category = "Quest",
                            difficulty = "Medium",
                            frequency = "Daily",
                            notes = quest.description,
                            targetDuration = quest.targetDuration,
                            preferredTime = ""
                        ),
                        viewModel = timerViewModel,
                        onDismiss = { showTimer = false },
                        onComplete = {
                            onClaim()
                            showTimer = false
                        }
                    )
                }
            }
        }
    }
}