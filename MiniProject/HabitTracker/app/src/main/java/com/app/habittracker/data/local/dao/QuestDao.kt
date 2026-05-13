package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.habittracker.data.local.entities.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests WHERE userId = :userId ORDER BY generatedDate DESC")
    fun getAllQuests(userId: String): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quests WHERE userId = :userId AND isCompleted = 0")
    fun getActiveQuests(userId: String): Flow<List<QuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: QuestEntity)

    @Update
    suspend fun updateQuest(quest: QuestEntity)

    @Query("DELETE FROM quests WHERE userId = :userId AND title = :title")
    suspend fun deleteQuestByTitle(userId: String, title: String)

    @Query("DELETE FROM quests WHERE userId = :userId")
    suspend fun clearAllQuests(userId: String)
}
