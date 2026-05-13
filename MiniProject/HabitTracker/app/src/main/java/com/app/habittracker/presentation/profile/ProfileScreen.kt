package com.app.habittracker.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.presentation.common.AtmosphereBackground
import com.app.habittracker.presentation.onboarding.DifficultyCard
import com.app.habittracker.presentation.onboarding.GoalCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val isLoggedOut by viewModel.isLoggedOut.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            onLogout()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AtmosphereBackground {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("Hero Settings", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface) },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                            }
                        },
                        actions = {
                            if (saveSuccess) {
                                Icon(Icons.Default.Check, contentDescription = "Saved", tint = Color(0xFF4CAF50), modifier = Modifier.padding(end = 16.dp))
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.onSurface,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            ) { padding ->
                user?.let { currentUser ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(24.dp)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Hero Identity
                        Text("Hero Name", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = currentUser.name,
                            onValueChange = viewModel::updateName,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Difficulty Level
                        Text("World Difficulty", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        val difficulties = listOf(
                            "Easy" to "10 XP per task. Best for beginners.",
                            "Medium" to "20 XP per task. Standard progression.",
                            "Hard" to "35 XP per task. High risk, high reward."
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            difficulties.forEach { (diff, desc) ->
                                DifficultyCard(
                                    difficulty = diff,
                                    description = desc,
                                    isSelected = currentUser.difficultyPreference == diff,
                                    onClick = { viewModel.updateDifficulty(diff) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Reminder Time
                        Text("Global Reminders", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        val times = listOf("06:00 AM", "08:00 AM", "12:00 PM", "05:00 PM", "08:00 PM")
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            times.forEach { time ->
                                GoalCard(
                                    goal = time,
                                    isSelected = currentUser.reminderTime == time,
                                    onClick = { viewModel.updateReminderTime(time) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Button(
                            onClick = viewModel::saveChanges,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("SAVE HERO SETTINGS", fontWeight = FontWeight.Black)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = viewModel::logout,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
                        ) {
                            Text("LOGOUT HERO", fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
