package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.QuestDao
import com.app.habittracker.data.local.entities.QuestEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao,
    private val preferencesManager: com.app.habittracker.data.local.PreferencesManager
) {
    private val currentUserIdFlow = preferencesManager.currentUserId

    fun getActiveQuests(): Flow<List<QuestEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) questDao.getActiveQuests(userId) else flowOf(emptyList())
    }

    fun getAllQuests(): Flow<List<QuestEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) questDao.getAllQuests(userId) else flowOf(emptyList())
    }

    suspend fun insertQuest(quest: QuestEntity) {
        val userId = currentUserIdFlow.first() ?: return
        questDao.insertQuest(quest.copy(userId = userId))
    }

    suspend fun updateQuest(quest: QuestEntity) {
        questDao.updateQuest(quest)
    }

    suspend fun deleteQuestByTitle(title: String) {
        val userId = currentUserIdFlow.first() ?: return
        questDao.deleteQuestByTitle(userId, title)
    }

    suspend fun clearAllQuests() {
        val userId = currentUserIdFlow.first() ?: return
        questDao.clearAllQuests(userId)
    }
}
