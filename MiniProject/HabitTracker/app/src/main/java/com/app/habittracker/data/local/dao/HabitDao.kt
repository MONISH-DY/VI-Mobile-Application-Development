package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.habittracker.data.local.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAllHabits(userId: String): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE userId = :userId AND isCompletedToday = 0 AND isMissedToday = 0")
    fun getPendingHabits(userId: String): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("UPDATE habits SET streak = 0, isMissedToday = 1 WHERE userId = :userId AND isCompletedToday = 0")
    suspend fun resetMissedStreaks(userId: String)

    @Query("UPDATE habits SET isCompletedToday = 0, isMissedToday = 0 WHERE userId = :userId")
    suspend fun resetDailyCompletions(userId: String)

    @Query("SELECT * FROM habits WHERE userId = :userId AND repeatDays LIKE '%' || :day || '%' AND isCompletedToday = 0 AND isMissedToday = 0")
    suspend fun getUnprocessedHabitsForDay(userId: String, day: String): List<HabitEntity>

    @Query("SELECT * FROM habits WHERE userId = :userId AND isMissedToday = 1")
    suspend fun getMissedHabits(userId: String): List<HabitEntity>

    @Query("UPDATE habits SET streak = streak + :amount WHERE userId = :userId")
    suspend fun incrementAllStreaks(userId: String, amount: Int)
}
