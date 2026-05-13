package com.app.habittracker.presentation.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import android.content.Context
import com.app.habittracker.workers.WorkScheduler
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class HabitCreationViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitCreationState())
    val uiState: StateFlow<HabitCreationState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title, titleError = null)
    }

    fun updateIcon(icon: String) {
        _uiState.value = _uiState.value.copy(icon = icon)
    }

    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun updateDifficulty(difficulty: String) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }

    fun updateFrequency(frequency: String) {
        _uiState.value = _uiState.value.copy(frequency = frequency)
    }

    fun updateTime(time: String) {
        _uiState.value = _uiState.value.copy(preferredTime = time)
    }

    fun updateStartTime(time: String) {
        _uiState.value = _uiState.value.copy(startTime = time)
    }

    fun updateEndTime(time: String) {
        _uiState.value = _uiState.value.copy(endTime = time)
    }

    fun updateRepeatDays(days: String) {
        _uiState.value = _uiState.value.copy(repeatDays = days)
    }

    fun updateTargetDuration(minutes: Int) {
        _uiState.value = _uiState.value.copy(targetDuration = minutes)
    }

    fun toggleRepeatDay(day: String) {
        val currentDays = _uiState.value.repeatDays.split(",").filter { it.isNotBlank() }.toMutableList()
        if (currentDays.contains(day)) {
            currentDays.remove(day)
        } else {
            currentDays.add(day)
        }
        _uiState.value = _uiState.value.copy(repeatDays = currentDays.joinToString(","))
    }

    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }

    fun saveHabit(onComplete: () -> Unit) {
        val state = _uiState.value
        
        // Form Validation
        if (state.title.isBlank()) {
            _uiState.value = _uiState.value.copy(titleError = "Title cannot be empty")
            return
        }

        if (state.startTime == state.endTime) {
            _uiState.value = _uiState.value.copy(titleError = "Start and End time cannot be the same")
            return
        }

        viewModelScope.launch {
            val habit = HabitEntity(
                title = state.title,
                icon = state.icon,
                category = state.category,
                difficulty = state.difficulty,
                frequency = state.frequency,
                repeatDays = state.repeatDays,
                startTime = state.startTime,
                endTime = state.endTime,
                targetDuration = state.targetDuration,
                preferredTime = state.preferredTime,
                notes = state.notes
            )
            val newId = habitRepository.insertHabit(habit)
            
            // Schedule the exact alarm immediately
            WorkScheduler.scheduleExactAlarm(context, habit.copy(id = newId.toInt()))
            
            // Trigger success animation
            _uiState.value = _uiState.value.copy(showSuccess = true)
            delay(1500) // wait for animation
            onComplete()
        }
    }
}

data class HabitCreationState(
    val title: String = "",
    val titleError: String? = null,
    val icon: String = "🎯",
    val category: String = "Fitness",
    val difficulty: String = "Medium",
    val frequency: String = "Daily",
    val repeatDays: String = "Sun,Mon,Tue,Wed,Thu,Fri,Sat",
    val startTime: String = "09:00 AM",
    val endTime: String = "09:00 PM",
    val targetDuration: Int = 0,
    val preferredTime: String = "Morning",
    val notes: String = "",
    val showSuccess: Boolean = false
)
