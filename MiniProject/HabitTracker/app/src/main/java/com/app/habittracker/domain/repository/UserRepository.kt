package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.UserDao
import com.app.habittracker.data.local.dao.XPHistoryDao
import com.app.habittracker.data.local.entities.UserEntity
import com.app.habittracker.data.local.entities.XPHistoryEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val xpHistoryDao: XPHistoryDao,
    private val preferencesManager: com.app.habittracker.data.local.PreferencesManager
) {
    fun getUser(): Flow<UserEntity?> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) userDao.getUserById(userId) else flowOf(null)
    }

    private val currentUserIdFlow = preferencesManager.currentUserId

    fun getXPHistory(): Flow<List<XPHistoryEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) xpHistoryDao.getHistory(userId) else flowOf(emptyList())
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    suspend fun addXP(amount: Int, reason: String) {
        val userId = kotlinx.coroutines.runBlocking { currentUserIdFlow.first() } ?: return
        userDao.incrementXP(userId, amount)
        xpHistoryDao.insertHistory(XPHistoryEntity(userId = userId, amount = amount, reason = reason))
        xpHistoryDao.deleteOldHistory(userId)
    }

    suspend fun updateLevel(newLevel: Int) {
        val userId = kotlinx.coroutines.runBlocking { currentUserIdFlow.first() } ?: return
        userDao.updateLevel(userId, newLevel)
    }

    suspend fun updateXPBoost(timestamp: Long) {
        val userId = kotlinx.coroutines.runBlocking { currentUserIdFlow.first() } ?: return
        userDao.updateXPBoost(userId, timestamp)
    }

    suspend fun updateSkin(skin: String) {
        val userId = kotlinx.coroutines.runBlocking { currentUserIdFlow.first() } ?: return
        userDao.updateSkin(userId, skin)
    }

    suspend fun updateProfileSettings(name: String, difficulty: String, reminderTime: String) {
        val userId = kotlinx.coroutines.runBlocking { currentUserIdFlow.first() } ?: return
        userDao.updateProfileSettings(userId, name, difficulty, reminderTime)
    }

    suspend fun createUser(name: String, difficulty: String, reminderTime: String) {
        // Initial user creation for onboarding (local only until they sign up)
        val tempId = "local_hero"
        userDao.insertUser(
            UserEntity(
                id = tempId,
                name = name,
                difficultyPreference = difficulty,
                reminderTime = reminderTime,
                phoneNumber = "",
                pin = "0000"
            )
        )
        preferencesManager.saveCurrentUserId(tempId)
    }

    suspend fun clearDummyHistory() {
        // No-op for now
    }
}
