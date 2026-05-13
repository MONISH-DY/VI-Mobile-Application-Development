package com.app.habittracker.domain.repository

import com.app.habittracker.data.local.dao.HabitDao
import com.app.habittracker.data.local.dao.HabitHistoryDao
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.data.local.entities.HabitHistoryEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val habitDao: HabitDao,
    private val habitHistoryDao: HabitHistoryDao,
    private val preferencesManager: com.app.habittracker.data.local.PreferencesManager
) {
    private val currentUserIdFlow = preferencesManager.currentUserId

    fun getAllHabits(): Flow<List<HabitEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) habitDao.getAllHabits(userId) else flowOf(emptyList())
    }

    fun getPendingHabits(): Flow<List<HabitEntity>> = currentUserIdFlow.flatMapLatest { userId ->
        if (userId != null) habitDao.getPendingHabits(userId) else flowOf(emptyList())
    }

    suspend fun getHabitById(id: Int): HabitEntity? = habitDao.getHabitById(id)

    suspend fun insertHabit(habit: HabitEntity): Long {
        val userId = currentUserIdFlow.first() ?: ""
        return habitDao.insertHabit(habit.copy(userId = userId))
    }

    suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    suspend fun resetDailyCompletions() {
        val userId = currentUserIdFlow.first() ?: return
        habitDao.resetMissedStreaks(userId)
        habitDao.resetDailyCompletions(userId)
    }

    suspend fun getMissedHabits(): List<HabitEntity> {
        val userId = currentUserIdFlow.first() ?: return emptyList()
        return habitDao.getMissedHabits(userId)
    }

    suspend fun getUnprocessedHabitsForDay(day: String): List<HabitEntity> {
        val userId = currentUserIdFlow.first() ?: return emptyList()
        return habitDao.getUnprocessedHabitsForDay(userId, day)
    }

    // --- History Tracking ---
    fun getAllHistory(): Flow<List<HabitHistoryEntity>> =
        currentUserIdFlow.flatMapLatest { userId ->
            if (userId != null) habitHistoryDao.getAllHistory(userId) else flowOf(emptyList())
        }

    fun getHistoryForMonth(yearMonth: String): Flow<List<HabitHistoryEntity>> =
        currentUserIdFlow.flatMapLatest { userId ->
            if (userId != null) habitHistoryDao.getHistoryForMonth(userId, yearMonth) else flowOf(
                emptyList()
            )
        }

    suspend fun logHabitCompletion(habitId: Int, dateStr: String, isCompleted: Boolean) {
        val userId = currentUserIdFlow.first() ?: return
        val existing = habitHistoryDao.getHistoryRecord(userId, dateStr, habitId)
        if (existing != null) {
            habitHistoryDao.insertHistory(existing.copy(isCompleted = isCompleted))
        } else {
            habitHistoryDao.insertHistory(
                HabitHistoryEntity(
                    userId = userId,
                    habitId = habitId,
                    dateStr = dateStr,
                    isCompleted = isCompleted
                )
            )
        }
    }

    suspend fun restoreHabitStreaks(amount: Int) {
        val userId = currentUserIdFlow.first() ?: return
        habitDao.incrementAllStreaks(userId, amount)
    }
}