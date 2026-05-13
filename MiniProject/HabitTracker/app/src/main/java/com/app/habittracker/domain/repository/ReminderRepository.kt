package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.ReminderDao
import com.app.habittracker.data.local.entities.ReminderEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val preferencesManager: com.app.habittracker.data.local.PreferencesManager
) {
    private val currentUserIdFlow = preferencesManager.currentUserId

    fun getPendingReminders(): Flow<List<ReminderEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) reminderDao.getPendingReminders(userId) else flowOf(emptyList())
    }

    suspend fun insertReminder(reminder: ReminderEntity) {
        val userId = currentUserIdFlow.first() ?: return
        reminderDao.insertReminder(reminder.copy(userId = userId))
    }

    suspend fun updateReminder(reminder: ReminderEntity) {
        reminderDao.updateReminder(reminder)
    }
    
    suspend fun clearSentReminders() {
        val userId = currentUserIdFlow.first() ?: return
        reminderDao.clearSentReminders(userId)
    }
}
