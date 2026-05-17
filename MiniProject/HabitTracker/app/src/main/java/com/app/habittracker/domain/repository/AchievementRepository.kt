package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.AchievementDao
import com.app.habittracker.data.local.entities.AchievementEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao,
    private val preferencesManager: com.app.habittracker.data.local.PreferencesManager
) {
    private val currentUserIdFlow = preferencesManager.currentUserId

    fun getAllAchievements(): Flow<List<AchievementEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) achievementDao.getAllAchievements(userId) else flowOf(emptyList())
    }

    suspend fun insertAchievement(achievement: AchievementEntity) {
        val userId = currentUserIdFlow.first() ?: return
        achievementDao.insertAchievement(achievement.copy(userId = userId))
    }

    suspend fun updateAchievement(achievement: AchievementEntity) {
        achievementDao.updateAchievement(achievement)
    }

    suspend fun getAchievementByTitle(title: String): AchievementEntity? {
        val userId = currentUserIdFlow.first() ?: return null
        return achievementDao.getAchievementByTitle(userId, title)
    }

    suspend fun clearDuplicates() {
        val userId = currentUserIdFlow.first() ?: return
        achievementDao.clearDuplicates(userId)
    }

    suspend fun initializeDefaultAchievements(userId: String) {
        val current = achievementDao.getAllAchievements(userId).first()
        if (current.isEmpty()) {
            val defaults = listOf(
                AchievementEntity(userId = userId, title = "First Habit", description = "Complete your very first habit!", icon = "⭐"),
                AchievementEntity(userId = userId, title = "7 Day Streak", description = "Maintain a habit for a full week!", icon = "🔥"),
                AchievementEntity(userId = userId, title = "Elite Streak", description = "Maintain a habit for 30 days!", icon = "👑"),
                AchievementEntity(userId = userId, title = "Level 5", description = "Reach Level 5!", icon = "🎖️"),
                AchievementEntity(userId = userId, title = "Level 10", description = "Reach Level 10!", icon = "🌟"),
                AchievementEntity(userId = userId, title = "Level 20", description = "Reach Level 20!", icon = "💎"),
                AchievementEntity(userId = userId, title = "Level 50", description = "Reach Level 50!", icon = "🚀"),
                AchievementEntity(userId = userId, title = "Boss Slayer", description = "Defeat your first Boss Battle!", icon = "⚔️"),
                AchievementEntity(userId = userId, title = "Perfect Week", description = "Complete all habits for 7 days.", icon = "💯"),
                AchievementEntity(userId = userId, title = "Comeback King", description = "Complete a Recovery Mission.", icon = "🦅")
            )
            defaults.forEach { achievementDao.insertAchievement(it) }
        }
    }
}
