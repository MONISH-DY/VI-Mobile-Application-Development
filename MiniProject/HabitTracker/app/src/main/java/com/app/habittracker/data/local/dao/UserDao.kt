package com.app.habittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.habittracker.data.local.entities.UserEntity
import com.app.habittracker.data.local.entities.XPHistoryEntity
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE phoneNumber = :phone AND pin = :pin")
    suspend fun getUserByPhoneAndPin(phone: String, pin: String): UserEntity?

    @Query("SELECT * FROM users WHERE phoneNumber = :phone")
    suspend fun getUserByPhone(phone: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET totalXp = totalXp + :amount WHERE id = :userId")
    suspend fun incrementXP(userId: String, amount: Int)

    @Query("UPDATE users SET currentLevel = :newLevel WHERE id = :userId")
    suspend fun updateLevel(userId: String, newLevel: Int)

    @Query("UPDATE users SET activeXpBoostUntil = :timestamp WHERE id = :userId")
    suspend fun updateXPBoost(userId: String, timestamp: Long)

    @Query("UPDATE users SET currentSparkySkin = :skin WHERE id = :userId")
    suspend fun updateSkin(userId: String, skin: String)

    @Query("UPDATE users SET name = :name, difficultyPreference = :difficulty, reminderTime = :reminderTime WHERE id = :userId")
    suspend fun updateProfileSettings(userId: String, name: String, difficulty: String, reminderTime: String)

    // --- Atomic XP Operations ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXPHistory(history: XPHistoryEntity)

    @androidx.room.Query("DELETE FROM xp_history WHERE userId = :userId AND id NOT IN (SELECT id FROM xp_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT 50)")
    suspend fun deleteOldXPHistory(userId: String)

    @Transaction
    suspend fun addXPWithHistory(userId: String, amount: Int, reason: String) {
        incrementXP(userId, amount)
        insertXPHistory(XPHistoryEntity(userId = userId, amount = amount, reason = reason))
        deleteOldXPHistory(userId)
    }
}
