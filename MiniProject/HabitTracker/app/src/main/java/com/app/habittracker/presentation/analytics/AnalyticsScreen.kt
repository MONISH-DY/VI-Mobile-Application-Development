package com.app.habittracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.app.habittracker.data.local.entities.XPHistoryEntity
import com.app.habittracker.ui.theme.PrimaryGradient
import com.app.habittracker.ui.theme.getAppBackgroundGradient
import com.app.habittracker.ui.theme.getAppGlassColor
import com.app.habittracker.ui.theme.getAppBorderColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToMasteryGallery: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val xpHistory by viewModel.xpHistory.collectAsState()

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
                                "Performance Insights",
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    // Analytics Summary Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(androidx.compose.ui.graphics.Brush.linearGradient(com.app.habittracker.ui.theme.getSignatureGradient()))
                            .padding(24.dp)
                    ) {
                        Column {
                            Text(
                                text = "Total XP Earned",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${maxOf(0, xpHistory.sumOf { it.amount })} XP",
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                IconButton(
                                    onClick = onNavigateToMasteryGallery,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                ) {
                                    Text("🏆", fontSize = 24.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Keep going to unlock next level!",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    // Weekly Progress Chart
                    Text(
                        text = "Weekly Progress",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(getAppGlassColor())
                            .border(1.dp, getAppBorderColor(), RoundedCornerShape(24.dp))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            val last14Days = (0..13).reversed().map { daysAgo ->
                                val cal = java.util.Calendar.getInstance()
                                cal.add(java.util.Calendar.DAY_OF_YEAR, -daysAgo)
                                val dateStr =
                                    SimpleDateFormat("dd/MM", Locale.getDefault()).format(cal.time)
                                val dayXp = xpHistory.filter {
                                    SimpleDateFormat(
                                        "dd/MM",
                                        Locale.getDefault()
                                    ).format(Date(it.timestamp)) == dateStr
                                }.sumOf { it.amount }
                                dateStr to dayXp
                            }

                            val currentWeek = last14Days.takeLast(7)
                            val ghostWeek = last14Days.take(7)

                            val maxXP = maxOf(100, last14Days.maxOf { it.second })

                            for (i in 0 until 7) {
                                val currentDayData = currentWeek.getOrNull(i) ?: continue
                                val ghostDayData = ghostWeek.getOrNull(i) ?: ("" to 0)

                                val xp = currentDayData.second
                                val ghostXp = ghostDayData.second
                                val dayLabel = currentDayData.first

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.weight(1f).fillMaxHeight()
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.BottomCenter
                                    ) {
                                        // GHOST BAR
                                        val ghostHeight =
                                            (ghostXp.toFloat() / maxXP.toFloat()).coerceIn(
                                                0.05f,
                                                0.9f
                                            )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.2f)
                                                .fillMaxHeight(ghostHeight)
                                                .alpha(0.15f)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(MaterialTheme.colorScheme.onSurface)
                                        )

                                        // CURRENT BAR
                                        val barHeight =
                                            (xp.toFloat() / maxXP.toFloat()).coerceIn(0.1f, 1f)
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.6f)
                                                .fillMaxHeight(barHeight)
                                                .clip(
                                                    RoundedCornerShape(
                                                        topStart = 8.dp,
                                                        topEnd = 8.dp
                                                    )
                                                )
                                                .background(
                                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                                        if (xp > ghostXp) listOf(
                                                            Color(0xFF4CAF50),
                                                            Color(0xFF81C784)
                                                        )
                                                        else listOf(
                                                            Color(0xFFD32F2F),
                                                            Color(0xFFE57373)
                                                        )
                                                    )
                                                )
                                        )
                                    }
                                    Text(
                                        text = dayLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "Recent Activity",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(xpHistory) { history ->
                            HistoryItem(history)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(history: XPHistoryEntity) {
    val isPositive = history.amount >= 0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(getAppGlassColor())
            .border(1.dp, getAppBorderColor(), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = if (isPositive) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color(
                    0xFFD32F2F
                ).copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (isPositive) "✨" else "⚠️",
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                Text(
                    text = history.reason,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(
                        Date(
                            history.timestamp
                        )
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = if (history.amount >= 0) "+${history.amount} XP" else "${history.amount} XP",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = if (history.amount >= 0) Color(0xFF4CAF50) else Color(0xFFD32F2F)
            )
        }
    }
}
