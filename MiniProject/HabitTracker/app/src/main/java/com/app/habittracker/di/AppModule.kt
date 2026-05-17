package com.app.habittracker.di

import android.app.Application
import androidx.room.Room
import com.app.habittracker.data.local.HabitDatabase
import com.app.habittracker.data.local.PreferencesManager
import com.app.habittracker.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @Singleton
    fun provideHabitDatabase(app: Application): HabitDatabase {
        return Room.databaseBuilder(
            app,
            HabitDatabase::class.java,
            HabitDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .addCallback(object : androidx.room.RoomDatabase.Callback() {
            override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onCreate(db)
                // Initialize default achievements
                val initialAchievements = listOf(
                    "('', 'First Habit', 'Complete your very first habit!', '⭐', 0, 0)",
                    "('', '7 Day Streak', 'Maintain a habit for a full week!', '🔥', 0, 0)",
                    "('', 'Elite Streak', 'Maintain a habit for 30 days!', '👑', 0, 0)",
                    "('', 'Level 5', 'Reach Level 5!', '🎖️', 0, 0)",
                    "('', 'Level 10', 'Reach Level 10!', '🌟', 0, 0)",
                    "('', 'Level 20', 'Reach Level 20!', '💎', 0, 0)",
                    "('', 'Level 50', 'Reach Level 50!', '🚀', 0, 0)",
                    "('', 'Boss Slayer', 'Defeat your first Boss Battle!', '⚔️', 0, 0)"
                )
                initialAchievements.forEach { values ->
                    db.execSQL("INSERT INTO achievements (userId, title, description, icon, isUnlocked, unlockedAt) VALUES $values")
                }
            }
        })
        .build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(db: HabitDatabase): HabitDao = db.habitDao

    @Provides
    @Singleton
    fun provideUserDao(db: HabitDatabase): UserDao = db.userDao

    @Provides
    @Singleton
    fun provideQuestDao(db: HabitDatabase): QuestDao = db.questDao

    @Provides
    @Singleton
    fun provideXPHistoryDao(db: HabitDatabase): XPHistoryDao = db.xpHistoryDao

    @Provides
    @Singleton
    fun provideAchievementDao(db: HabitDatabase): AchievementDao = db.achievementDao

    @Provides
    @Singleton
    fun provideBossBattleDao(db: HabitDatabase): BossBattleDao = db.bossBattleDao

    @Provides
    @Singleton
    fun provideReminderDao(db: HabitDatabase): ReminderDao = db.reminderDao

    @Provides
    @Singleton
    fun provideHabitHistoryDao(db: HabitDatabase): HabitHistoryDao = db.habitHistoryDao

    @Provides
    @Singleton
    fun providePreferencesManager(app: Application): PreferencesManager {
        return PreferencesManager(app)
    }
}
