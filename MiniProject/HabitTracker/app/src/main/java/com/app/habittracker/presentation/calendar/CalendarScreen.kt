package com.app.habittracker.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.app.habittracker.presentation.common.AtmosphereBackground
import com.app.habittracker.ui.theme.getAppGlassColor
import com.app.habittracker.ui.theme.getAppBorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onNavigateBack: () -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val performanceByDate by viewModel.dailyPerformance.collectAsState()
    val allHabits by viewModel.allHabits.collectAsState()
    val historyByDate by viewModel.historyByDate.collectAsState()

    val calendar = Calendar.getInstance()
    val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(calendar.time)
    
    // Generate dates for current month
    val dates = remember { generateDatesForMonth(calendar) }

    var selectedDate by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AtmosphereBackground {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("History & Calendar", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface) },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
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
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = currentMonth,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Days of week header
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                            Text(text = it, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)
                    ) {
                        items(dates) { dateObj ->
                            if (dateObj == null) {
                                Spacer(modifier = Modifier.size(40.dp))
                            } else {
                                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dateObj)
                                val performance = performanceByDate[dateStr]
                                val isSelected = selectedDate == dateStr
                                
                                val bgColor = when {
                                    performance == null -> Color.DarkGray.copy(alpha = 0.3f)
                                    performance == 1.0f -> Color(0xFF00C853) // Perfect
                                    performance >= 0.5f -> Color(0xFFFFD700) // Partial
                                    else -> Color(0xFFFF3333) // Missed
                                }

                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(CircleShape)
                                        .background(bgColor)
                                        .border(
                                            width = if (isSelected) 2.dp else 0.dp,
                                            color = if (isSelected) Color.White else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable { selectedDate = dateStr },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = Calendar.getInstance().apply { time = dateObj }.get(Calendar.DAY_OF_MONTH).toString(),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (performance != null) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Detail View
                    if (selectedDate != null) {
                        val records = historyByDate[selectedDate] ?: emptyList()
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = getAppGlassColor()),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, getAppBorderColor())
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Habits for $selectedDate",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                if (records.isEmpty()) {
                                    Text("No tracking data for this day.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                } else {
                                    records.forEach { record ->
                                        val habit = allHabits.find { it.id == record.habitId }
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(if (record.isCompleted) "✅" else "❌", modifier = Modifier.padding(end = 8.dp))
                                            Text(
                                                text = habit?.title ?: "Unknown Habit",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = if (record.isCompleted) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                                textDecoration = if (record.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun generateDatesForMonth(calendar: Calendar): List<Date?> {
    val cal = calendar.clone() as Calendar
    cal.set(Calendar.DAY_OF_MONTH, 1)
    
    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1 // 0-based for Sun-Sat
    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    
    val list = mutableListOf<Date?>()
    for (i in 0 until firstDayOfWeek) {
        list.add(null)
    }
    for (i in 1..daysInMonth) {
        cal.set(Calendar.DAY_OF_MONTH, i)
        list.add(cal.time)
    }
    return list
}
