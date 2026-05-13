package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.habittracker.data.local.entities.HabitHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitHistoryDao {
    @Query("SELECT * FROM habit_history WHERE userId = :userId ORDER BY dateStr DESC")
    fun getAllHistory(userId: String): Flow<List<HabitHistoryEntity>>

    @Query("SELECT * FROM habit_history WHERE userId = :userId AND dateStr LIKE :yearMonth || '%'")
    fun getHistoryForMonth(userId: String, yearMonth: String): Flow<List<HabitHistoryEntity>>

    @Query("SELECT * FROM habit_history WHERE userId = :userId AND habitId = :habitId")
    fun getHistoryForHabit(userId: String, habitId: Int): Flow<List<HabitHistoryEntity>>

    @Query("SELECT * FROM habit_history WHERE userId = :userId AND dateStr = :dateStr AND habitId = :habitId LIMIT 1")
    suspend fun getHistoryRecord(userId: String, dateStr: String, habitId: Int): HabitHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HabitHistoryEntity)
}
