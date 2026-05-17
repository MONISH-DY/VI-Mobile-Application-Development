package com.app.habittracker.presentation.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.AchievementEntity
import com.app.habittracker.domain.repository.AchievementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val gamificationEngine: com.app.habittracker.domain.engines.GamificationEngine
) : ViewModel() {

    val achievements: StateFlow<List<AchievementEntity>> = achievementRepository.getAllAchievements()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            gamificationEngine.syncAchievements()
        }
    }

    fun refreshSync() {
        viewModelScope.launch {
            gamificationEngine.syncAchievements()
        }
    }
}
