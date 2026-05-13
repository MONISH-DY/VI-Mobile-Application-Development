package com.app.habittracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.habittracker.data.local.dao.*
import com.app.habittracker.data.local.entities.*

@Database(
    entities = [
        UserEntity::class,
        HabitEntity::class,
        QuestEntity::class,
        XPHistoryEntity::class,
        AchievementEntity::class,
        BossBattleEntity::class,
        ReminderEntity::class,
        HabitHistoryEntity::class
    ],
    version = 11,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {
    abstract val habitDao: HabitDao
    abstract val userDao: UserDao
    abstract val questDao: QuestDao
    abstract val xpHistoryDao: XPHistoryDao
    abstract val achievementDao: AchievementDao
    abstract val bossBattleDao: BossBattleDao
    abstract val reminderDao: ReminderDao
    abstract val habitHistoryDao: HabitHistoryDao

    companion object {
        const val DATABASE_NAME = "habit_tracker_db"
    }
}
