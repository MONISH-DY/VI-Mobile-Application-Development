package com.app.habittracker.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.PreferencesManager
import com.app.habittracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun selectGoal(goal: String) {
        val currentGoals = _uiState.value.selectedGoals.toMutableSet()
        if (currentGoals.contains(goal)) {
            currentGoals.remove(goal)
        } else {
            currentGoals.add(goal)
        }
        _uiState.value = _uiState.value.copy(selectedGoals = currentGoals.toList())
    }

    fun selectDifficulty(difficulty: String) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }

    fun updateReminderTime(time: String) {
        _uiState.value = _uiState.value.copy(reminderTime = time)
    }

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            userRepository.createUser(
                name = state.name.ifBlank { "Hero" },
                difficulty = state.difficulty,
                reminderTime = state.reminderTime
            )
            preferencesManager.saveOnboardingCompleted(true)
            onComplete()
        }
    }
}

data class OnboardingState(
    val name: String = "",
    val selectedGoals: List<String> = emptyList(),
    val difficulty: String = "Medium",
    val reminderTime: String = "08:00 AM"
)
