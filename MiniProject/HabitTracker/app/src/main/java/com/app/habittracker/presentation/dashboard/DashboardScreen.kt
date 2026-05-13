package com.app.habittracker.presentation.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.data.local.entities.*
import com.app.habittracker.presentation.common.*
import com.app.habittracker.presentation.dashboard.components.*
import com.app.habittracker.util.TimeUtils
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToHabitCreation: () -> Unit,
    onNavigateToQuests: () -> Unit,
    onNavigateToBossBattle: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
    timerViewModel: GlobalTimerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val xpEarned by viewModel.xpEarnedEvent.collectAsState()
    val levelUp by viewModel.levelUpEvent.collectAsState()
    val levelDown by viewModel.levelDownEvent.collectAsState()
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(xpEarned, levelUp, levelDown) {
        if (xpEarned != null || levelUp != null || levelDown != null) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    LaunchedEffect(Unit) {
        timerViewModel.timerFinishedEvent.collect { finishedTitle ->
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            val state = uiState as? DashboardUiState.Success
            state?.pendingHabits?.find { it.title == finishedTitle }?.let { habit ->
                viewModel.completeHabit(habit)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AtmosphereBackground {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    DashboardTopBar(
                        uiState = uiState,
                        onToggleTheme = viewModel::toggleTheme,
                        onNavigateToAnalytics = onNavigateToAnalytics,
                        onNavigateToAchievements = onNavigateToAchievements,
                        onNavigateToProfile = onNavigateToProfile,
                        onNavigateToCalendar = onNavigateToCalendar
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onNavigateToHabitCreation,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    ) { Icon(Icons.Default.Add, contentDescription = "Add Habit") }
                }
            ) { padding ->
                when (uiState) {
                    is DashboardUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is DashboardUiState.Success -> {
                        val state = uiState as DashboardUiState.Success
                        Box(
                            modifier = Modifier.padding(padding)
                        ) {
                            DashboardContent(
                                user = state.user,
                                habits = state.pendingHabits,
                                quests = state.activeQuests,
                                boss = state.currentBoss,
                                timerViewModel = timerViewModel,
                                onCompleteHabit = viewModel::completeHabit,
                                onDeleteHabit = viewModel::deleteHabit,
                                onNavigateToQuests = onNavigateToQuests,
                                onNavigateToBossBattle = onNavigateToBossBattle,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            // Popups & Overlays
            val achievement by viewModel.achievementUnlockedEvent.collectAsState()
            val evolvingHabit by viewModel.habitEvolutionEvent.collectAsState()
            val isComboActive by viewModel.comboEvent.collectAsState()
            val lootMessage by viewModel.lootMessage.collectAsState()

            DashboardPopups(
                xpEarned = xpEarned,
                levelUp = levelUp,
                levelDown = levelDown,
                achievement = achievement,
                evolvingHabit = evolvingHabit,
                isComboActive = isComboActive,
                lootMessage = lootMessage,
                onDismissLoot = viewModel::dismissLoot,
                onDismissEvolution = viewModel::dismissEvolution,
                onDismissLevelUp = viewModel::dismissLevelUp,
                onDismissLevelDown = viewModel::dismissLevelDown,
                onEvolveHabit = viewModel::evolveHabit
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    uiState: DashboardUiState,
    onToggleTheme: (Boolean) -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCalendar: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UniqueBrandLogo(size = 32.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Phoenix Spark", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface)
            }
        },
        actions = {
            val successState = uiState as? DashboardUiState.Success
            val user = successState?.user
            val isDark = successState?.isDarkMode ?: true
            var showStreakDialog by remember { mutableStateOf(false) }

            if (user != null) {
                val streakColor = when {
                    user.currentStreak >= 30 -> Color(0xFF00E5FF)
                    user.currentStreak >= 14 -> Color(0xFFFF3D00)
                    else -> Color(0xFFFF9100)
                }
                val infiniteTransition = rememberInfiniteTransition(label = "streak")
                val glowAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.4f, targetValue = 0.8f,
                    animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse), label = "glow"
                )

                TextButton(onClick = { showStreakDialog = true }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (user.currentStreak >= 30) Icons.Filled.Diamond else Icons.Filled.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = streakColor,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer { if (user.currentStreak >= 14) { shadowElevation = 8f; alpha = glowAlpha } }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${user.currentStreak}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = streakColor)
                    }
                }

                if (showStreakDialog) {
                    AlertDialog(
                        onDismissRequest = { showStreakDialog = false },
                        title = { Text("Daily Streak 🔥") },
                        text = {
                            Column {
                                Text("Current Streak: ${user.currentStreak} Days")
                                Text("Max Streak: ${user.highestStreak} Days", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(if (user.currentStreak == user.highestStreak && user.currentStreak > 0) "You're at your peak! Keep it up!" else "Consistency is key to mastery!")
                            }
                        },
                        confirmButton = { TextButton(onClick = { showStreakDialog = false }) { Text("Keep Burning!") } }
                    )
                }
            }

            IconButton(onClick = { onToggleTheme(isDark) }) {
                Icon(
                    imageVector = if (isDark) Icons.Filled.NightsStay else Icons.Filled.WbSunny,
                    contentDescription = "Toggle Theme",
                    tint = if (isDark) Color(0xFFE2E8F0) else Color(0xFFF59E0B),
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Box {
                var showMenu by remember { mutableStateOf(false) }
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onSurface)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    DropdownMenuItem(
                        text = { Text("Calendar", color = MaterialTheme.colorScheme.onSurface) },
                        leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) },
                        onClick = { showMenu = false; onNavigateToCalendar() }
                    )
                    DropdownMenuItem(
                        text = { Text("Analytics", color = MaterialTheme.colorScheme.onSurface) },
                        leadingIcon = { Icon(Icons.Filled.Timeline, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp)) },
                        onClick = { showMenu = false; onNavigateToAnalytics() }
                    )
                    DropdownMenuItem(
                        text = { Text("Achievements", color = MaterialTheme.colorScheme.onSurface) },
                        leadingIcon = { Icon(Icons.Filled.EmojiEvents, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(20.dp)) },
                        onClick = { showMenu = false; onNavigateToAchievements() }
                    )
                    DropdownMenuItem(
                        text = { Text("Profile", color = MaterialTheme.colorScheme.onSurface) },
                        leadingIcon = { Icon(Icons.Filled.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp)) },
                        onClick = { showMenu = false; onNavigateToProfile() }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent, titleContentColor = MaterialTheme.colorScheme.onSurface)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    user: UserEntity,
    habits: List<HabitEntity>,
    quests: List<QuestEntity>,
    boss: BossBattleEntity?,
    timerViewModel: GlobalTimerViewModel,
    onCompleteHabit: (HabitEntity) -> Unit,
    onDeleteHabit: (HabitEntity) -> Unit,
    onNavigateToQuests: () -> Unit,
    onNavigateToBossBattle: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val greeting = when (hour) { in 5..11 -> "Good Morning"; in 12..16 -> "Good Afternoon"; else -> "Good Evening" }
            Text(
                text = "$greeting, ${user.name}!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = if (user.currentStreak >= 3) "You're on a ${user.currentStreak}-day streak! Keep it burning! 🔥" else "Ready to crush some habits today?",
                style = MaterialTheme.typography.bodyMedium,
                color = if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFF38BDF8) else Color(0xFF0F172A)
            )
        }

        item { UserStatsSection(user = user) }
        item { DashboardWidgetsRow(quests = quests, boss = boss, onNavigateToQuests = onNavigateToQuests, onNavigateToBossBattle = onNavigateToBossBattle) }

        item {
            Text(text = "Today's Habits", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        }

        if (habits.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SparkyAvatar(level = user.currentLevel, skin = user.currentSparkySkin)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Habits Left!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Time to relax or start something new.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        } else {
            items(habits, key = { it.id }) { habit ->
                val currentTime = remember { SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Calendar.getInstance().time) }
                val isActive = currentTime >= TimeUtils.formatTo24h(habit.startTime) && currentTime <= TimeUtils.formatTo24h(habit.endTime)
                val isCompleted = habit.isCompletedToday

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when (it) {
                            SwipeToDismissBoxValue.StartToEnd -> { if (!isActive || isCompleted) false else { onCompleteHabit(habit); true } }
                            SwipeToDismissBoxValue.EndToStart -> { onDeleteHabit(habit); true }
                            else -> false
                        }
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = isActive && !isCompleted,
                    enableDismissFromEndToStart = true,
                    backgroundContent = {
                        val swipeBackgroundColor by animateColorAsState(
                            when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.StartToEnd -> Color.Green.copy(alpha = 0.5f)
                                SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.5f)
                                else -> Color.Transparent
                            }, label = "color"
                        )
                        Box(
                            Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp)).background(color = swipeBackgroundColor).padding(horizontal = 20.dp),
                            contentAlignment = if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                        ) {
                            if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) {
                                Text(if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) "DONE ✅" else "DELETE 🗑️", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    },
                    content = {
                        var showTimer by remember { mutableStateOf(false) }
                        HabitCard(habit = habit, timerViewModel = timerViewModel, onComplete = { if (habit.targetDuration > 0) showTimer = true else onCompleteHabit(habit) })
                        if (showTimer) {
                            TimerDialog(habit = habit, viewModel = timerViewModel, onDismiss = { showTimer = false }, onComplete = { onCompleteHabit(habit); showTimer = false })
                        }
                    }
                )
            }
        }
    }
}
