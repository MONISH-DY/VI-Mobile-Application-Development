package com.app.habittracker.presentation.mastery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.presentation.common.AtmosphereBackground
import com.app.habittracker.ui.theme.getAppBorderColor
import com.app.habittracker.ui.theme.getAppGlassColor
import com.app.habittracker.ui.theme.getSignatureGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasteryGalleryScreen(
    onNavigateBack: () -> Unit,
    viewModel: MasteryViewModel = hiltViewModel()
) {
    val masteredHabits by viewModel.masteredHabits.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AtmosphereBackground {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("Mastery Gallery", fontWeight = FontWeight.Black) },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                }
            ) { padding ->
                if (masteredHabits.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🛡️", fontSize = 80.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No Mastered Habits Yet", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text("Reach a 30-day streak to immortalize a habit!", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize().padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(masteredHabits) { habit ->
                            MasteryCard(habit)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MasteryCard(habit: HabitEntity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(24.dp))
            .background(getAppGlassColor())
            .border(2.dp, Brush.linearGradient(listOf(Color(0xFFFFD700), Color(0xFFFFA000))), RoundedCornerShape(24.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(64.dp).background(Brush.linearGradient(getSignatureGradient()), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(habit.icon, fontSize = 32.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(habit.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            Text("MASTERED", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFFA000), fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${habit.streak} DAY STREAK", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
    }
}
