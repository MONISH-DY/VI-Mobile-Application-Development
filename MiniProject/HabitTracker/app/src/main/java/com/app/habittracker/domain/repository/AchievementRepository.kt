package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.AchievementDao
import com.app.habittracker.data.local.entities.AchievementEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

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
}
