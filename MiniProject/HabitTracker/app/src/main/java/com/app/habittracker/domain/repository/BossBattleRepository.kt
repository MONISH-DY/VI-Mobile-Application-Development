package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.BossBattleDao
import com.app.habittracker.data.local.entities.BossBattleEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BossBattleRepository @Inject constructor(
    private val bossBattleDao: BossBattleDao,
    private val preferencesManager: com.app.habittracker.data.local.PreferencesManager
) {
    private val currentUserIdFlow = preferencesManager.currentUserId

    fun getCurrentBossBattle(): Flow<BossBattleEntity?> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) bossBattleDao.getCurrentBossBattle(userId) else flowOf(null)
    }

    suspend fun insertBossBattle(bossBattle: BossBattleEntity) {
        val userId = currentUserIdFlow.first() ?: return
        bossBattleDao.insertBossBattle(bossBattle.copy(userId = userId))
    }

    suspend fun updateBossBattle(bossBattle: BossBattleEntity) {
        bossBattleDao.updateBossBattle(bossBattle)
    }
}
