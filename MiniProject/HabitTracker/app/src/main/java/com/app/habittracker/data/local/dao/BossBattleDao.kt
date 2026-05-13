package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.habittracker.data.local.entities.BossBattleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BossBattleDao {
    @Query("SELECT * FROM boss_battles WHERE userId = :userId ORDER BY id DESC LIMIT 1")
    fun getCurrentBossBattle(userId: String): Flow<BossBattleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBossBattle(bossBattle: BossBattleEntity)

    @Update
    suspend fun updateBossBattle(bossBattle: BossBattleEntity)
}
