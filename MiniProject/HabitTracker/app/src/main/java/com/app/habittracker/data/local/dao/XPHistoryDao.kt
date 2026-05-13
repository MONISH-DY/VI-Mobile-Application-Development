package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.habittracker.data.local.entities.XPHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface XPHistoryDao {
    @Query("SELECT * FROM xp_history WHERE userId = :userId ORDER BY timestamp DESC")
    fun getHistory(userId: String): Flow<List<XPHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: XPHistoryEntity)

    @Query("DELETE FROM xp_history WHERE userId = :userId AND id NOT IN (SELECT id FROM xp_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT 200)")
    suspend fun deleteOldHistory(userId: String)

    @Query("DELETE FROM xp_history WHERE userId = :userId")
    suspend fun clearHistory(userId: String)
}
