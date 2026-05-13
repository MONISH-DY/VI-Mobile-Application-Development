package com.app.habittracker.presentation.habits

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCreationScreen(
    onNavigateBack: () -> Unit,
    viewModel: HabitCreationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

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
                                "Create Habit",
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
                            containerColor = Color.Transparent
                        )
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Title Input
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = viewModel::updateTitle,
                        label = { Text("Habit Title", color = MaterialTheme.colorScheme.onSurface) },
                        placeholder = { Text("e.g. Read 20 pages", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        isError = uiState.titleError != null,
                        supportingText = {
                            if (uiState.titleError != null) {
                                Text(
                                    text = uiState.titleError!!,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    // Icon Selection (Horizontal Slider)
                    Text(
                        "Icon",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    var showCustomEmojiDialog by remember { mutableStateOf(false) }
                    var customEmojiInput by remember { mutableStateOf("") }

                    if (showCustomEmojiDialog) {
                        AlertDialog(
                            onDismissRequest = { showCustomEmojiDialog = false },
                            title = { Text("Custom Emoji") },
                            text = {
                                OutlinedTextField(
                                    value = customEmojiInput,
                                    onValueChange = { if (it.length <= 2) customEmojiInput = it },
                                    placeholder = { Text("Enter emoji") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    if (customEmojiInput.isNotEmpty()) {
                                        viewModel.updateIcon(customEmojiInput)
                                        showCustomEmojiDialog = false
                                    }
                                }) { Text("OK") }
                            },
                            dismissButton = {
                                TextButton(onClick = { showCustomEmojiDialog = false }) { Text("Cancel") }
                            }
                        )
                    }

                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        val icons = listOf(
                            "🎯", "💪", "📚", "🧘", "⚡", "💧", "🎨", "🍎", "🏃", "🚶", "🚴", "🏊", "🎹", "💻", "🌱", "🍳", "🧹"
                        )
                        items(icons.size) { index ->
                            val icon = icons[index]
                            IconBox(icon = icon, isSelected = uiState.icon == icon, onClick = { viewModel.updateIcon(icon) })
                        }
                        item {
                            val isCustomIcon = !icons.contains(uiState.icon)
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isCustomIcon) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) 
                                        else com.app.habittracker.ui.theme.getAppGlassColor()
                                    )
                                    .border(
                                        1.dp,
                                        if (isCustomIcon) MaterialTheme.colorScheme.primary else com.app.habittracker.ui.theme.getAppBorderColor(),
                                        CircleShape
                                    )
                                    .clickable { showCustomEmojiDialog = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = if (isCustomIcon) uiState.icon else "+", fontSize = 28.sp)
                            }
                        }
                    }

                    // Category Selection
                    Text(
                        "Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val categories =
                        listOf("Health", "Work", "Social", "Mind", "Finance", "Creative", "Other")
                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(categories.size) { index ->
                            val cat = categories[index]
                            val isSelected = uiState.category == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.updateCategory(cat) },
                                label = { Text(cat) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    labelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }

                    // Difficulty Selection
                    Text(
                        "Difficulty",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf("Easy", "Medium", "Hard").forEach { diff ->
                            val isSelected = uiState.difficulty == diff
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.updateDifficulty(diff) },
                                label = { Text(diff) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    labelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }

                    // Frequency Selection
                    Text(
                        "Repeat",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf("Daily", "Weekly").forEach { freq ->
                            val isSelected = uiState.frequency == freq
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.updateFrequency(freq) },
                                label = { Text(freq) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    labelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }

                    if (uiState.frequency == "Weekly") {
                        Text(
                            "Repeat Days",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            days.forEach { day ->
                                val isSelected = uiState.repeatDays.contains(day)
                                Box(
                                    modifier = Modifier
                                        .size(width = 52.dp, height = 36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.2f
                                            ) else com.app.habittracker.ui.theme.getAppGlassColor()
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else com.app.habittracker.ui.theme.getAppBorderColor(),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { viewModel.toggleRepeatDay(day) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        day,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Time Range Selection
                    Text(
                        "Active Time Window",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    var showStartTimePicker by remember { mutableStateOf(false) }
                    var showEndTimePicker by remember { mutableStateOf(false) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "From",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(com.app.habittracker.ui.theme.getAppGlassColor())
                                    .border(
                                        1.dp,
                                        com.app.habittracker.ui.theme.getAppBorderColor(),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { showStartTimePicker = true }
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    uiState.startTime.ifEmpty { "Select Time" },
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "To",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(com.app.habittracker.ui.theme.getAppGlassColor())
                                    .border(
                                        1.dp,
                                        com.app.habittracker.ui.theme.getAppBorderColor(),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { showEndTimePicker = true }
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    uiState.endTime.ifEmpty { "Select Time" },
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    if (showStartTimePicker) {
                        AppTimePicker(
                            onDismiss = { showStartTimePicker = false },
                            onTimeSelected = { hour, minute ->
                                viewModel.updateStartTime(formatTo12h(hour, minute))
                                showStartTimePicker = false
                            }
                        )
                    }
                    if (showEndTimePicker) {
                        AppTimePicker(
                            onDismiss = { showEndTimePicker = false },
                            onTimeSelected = { hour, minute ->
                                viewModel.updateEndTime(formatTo12h(hour, minute))
                                showEndTimePicker = false
                            }
                        )
                    }

                    // Target Duration (Timer) - Text Input
                    Text(
                        "Target Duration (Minutes)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    OutlinedTextField(
                        value = if (uiState.targetDuration == 0) "" else uiState.targetDuration.toString(),
                        onValueChange = {
                            val duration = it.filter { char -> char.isDigit() }.toIntOrNull() ?: 0
                            viewModel.updateTargetDuration(duration)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        placeholder = {
                            Text(
                                "e.g. 15",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                        ),
                        trailingIcon = {
                            Text(
                                "min",
                                modifier = Modifier.padding(end = 16.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(androidx.compose.ui.graphics.Brush.linearGradient(com.app.habittracker.ui.theme.getSignatureGradient()))
                            .clickable { viewModel.saveHabit(onNavigateBack) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CREATE HABIT",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }
            }

            // Success Animation Overlay
            AnimatedVisibility(
                visible = uiState.showSuccess,
                enter = fadeIn(tween(300)) + scaleIn(tween(500)),
                exit = fadeOut(tween(300)) + scaleOut(tween(500)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Habit Created!",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppTimePicker(
        onDismiss: () -> Unit,
        onTimeSelected: (Int, Int) -> Unit
    ) {
        val timePickerState = rememberTimePickerState()

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelected(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TimePicker(state = timePickerState)
            }
        }
    }

    fun formatTo12h(hour: Int, minute: Int): String {
        val amPm = if (hour < 12) "AM" else "PM"
        val h = if (hour == 0 || hour == 12) 12 else hour % 12
        return String.format("%02d:%02d %s", h, minute, amPm)
    }

    @Composable
    @OptIn(ExperimentalLayoutApi::class)
    private fun FlowRow(
        horizontalArrangement: Arrangement.Horizontal,
        verticalArrangement: Arrangement.Vertical,
        content: @Composable () -> Unit
    ) {
        androidx.compose.foundation.layout.FlowRow(
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            content = { content() }
        )
    }

    @Composable
    private fun IconBox(icon: String, isSelected: Boolean, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    else com.app.habittracker.ui.theme.getAppGlassColor()
                )
                .border(
                    1.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary else com.app.habittracker.ui.theme.getAppBorderColor(),
                    CircleShape
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 28.sp)
        }
    }

