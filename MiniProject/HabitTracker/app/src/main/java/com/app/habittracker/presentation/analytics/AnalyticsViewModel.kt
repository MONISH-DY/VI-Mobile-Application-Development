package com.app.habittracker.presentation.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.XPHistoryEntity
import com.app.habittracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val xpHistory: StateFlow<List<XPHistoryEntity>> = userRepository.getXPHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            userRepository.clearDummyHistory()
            refreshData()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            val allHistory = userRepository.getXPHistory().first()
            val totalXp = maxOf(0, allHistory.sumOf { it.amount })
            
            val user = userRepository.getUser().firstOrNull()
            if (user != null && user.totalXp != totalXp) {
                val level = (totalXp / 100) + 1 // GamificationEngine.XP_PER_LEVEL is 100
                userRepository.updateUser(user.copy(totalXp = totalXp, currentLevel = level))
            }
        }
    }
}
