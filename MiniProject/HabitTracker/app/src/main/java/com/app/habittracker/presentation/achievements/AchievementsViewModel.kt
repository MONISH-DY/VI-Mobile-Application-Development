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
        seedDummyAchievements()
        viewModelScope.launch {
            gamificationEngine.syncAchievements()
        }
    }

    fun seedDummyAchievements() {
        viewModelScope.launch {
            achievementRepository.clearDuplicates()
            
            val existing = achievementRepository.getAllAchievements().first()
            val existingTitles = existing.map { it.title }.toSet()
            
            val list = listOf(
                AchievementEntity(title = "First Habit", description = "Completed your very first habit.", icon = "🌱", isUnlocked = false),
                AchievementEntity(title = "7 Day Streak", description = "Maintained a streak for a full week.", icon = "🔥", isUnlocked = false),
                AchievementEntity(title = "Level 5", description = "Reached Level 5.", icon = "⭐", isUnlocked = false),
                AchievementEntity(title = "Level 10", description = "Reached Level 10.", icon = "🌟", isUnlocked = false),
                AchievementEntity(title = "Level 20", description = "Reached Level 20.", icon = "✨", isUnlocked = false),
                AchievementEntity(title = "Level 50", description = "Reached Level 50.", icon = "👑", isUnlocked = false),
                AchievementEntity(title = "Perfect Week", description = "Completed all habits for 7 days.", icon = "💯", isUnlocked = false),
                AchievementEntity(title = "Boss Slayer", description = "Defeated the Weekly Boss.", icon = "🗡️", isUnlocked = false),
                AchievementEntity(title = "Comeback King", description = "Completed a Recovery Mission.", icon = "🦅", isUnlocked = false)
            )
            
            list.filter { it.title !in existingTitles }.forEach { 
                achievementRepository.insertAchievement(it) 
            }
        }
    }
    fun refreshSync() {
        viewModelScope.launch {
            gamificationEngine.syncAchievements()
        }
    }
}
