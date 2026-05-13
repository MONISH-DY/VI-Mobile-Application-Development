package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.habittracker.data.local.entities.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements WHERE userId = :userId")
    fun getAllAchievements(userId: String): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: AchievementEntity)

    @Update
    suspend fun updateAchievement(achievement: AchievementEntity)

    @Query("SELECT * FROM achievements WHERE userId = :userId AND title = :title LIMIT 1")
    suspend fun getAchievementByTitle(userId: String, title: String): AchievementEntity?

    @Query("DELETE FROM achievements WHERE userId = :userId AND id NOT IN (SELECT MIN(id) FROM achievements WHERE userId = :userId GROUP BY title)")
    suspend fun clearDuplicates(userId: String)
}
