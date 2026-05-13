package com.app.habittracker.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.habittracker.data.local.entities.BossBattleEntity
import com.app.habittracker.data.local.entities.QuestEntity
import com.app.habittracker.domain.repository.BossBattleRepository
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.domain.repository.QuestRepository
import com.app.habittracker.domain.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

import com.app.habittracker.domain.engines.GamificationEngine

@HiltWorker
class DailyResetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository,
    private val questRepository: QuestRepository,
    private val bossBattleRepository: BossBattleRepository,
    private val userRepository: UserRepository,
    private val gamificationEngine: GamificationEngine,
    private val smartQuestEngine: com.app.habittracker.domain.engines.SmartQuestEngine
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val calendarYesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
            val yesterdayDayStr = SimpleDateFormat("EEE", Locale.ENGLISH).format(calendarYesterday.time)
            
            // 0. Get all habits for processing
            val habits = habitRepository.getAllHabits().first()
            
            // 1. Process User Streak and Habit Streaks
            val habitsDueYesterday = habits.filter { it.repeatDays.contains(yesterdayDayStr) }
            val allCompletedYesterday = habitsDueYesterday.isNotEmpty() && habitsDueYesterday.all { it.isCompletedToday }
            
            val user = userRepository.getUser().first() ?: return Result.success()
            val newStreak = if (allCompletedYesterday) user.currentStreak + 1 else 0
            val newHighest = maxOf(user.highestStreak, newStreak)
            userRepository.updateUser(user.copy(currentStreak = newStreak, highestStreak = newHighest))

            val yesterdayDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendarYesterday.time)
            
            // Reset individual habit streaks only if they were DUE yesterday and MISSED
            habits.forEach { habit ->
                if (habit.repeatDays.contains(yesterdayDayStr) && !habit.isCompletedToday && !habit.isMissedToday) {
                    habitRepository.updateHabit(habit.copy(streak = 0, isMissedToday = true))
                    habitRepository.logHabitCompletion(habit.id, yesterdayDateStr, false)
                    // Deduct XP for missed habit
                    gamificationEngine.awardXP(-15, "Missed Habit: ${habit.title}")
                }
            }

            // 2. Reset the 'isCompletedToday' flag for all habits
            habitRepository.resetDailyCompletions()
            
            // 3. Clear and Generate Smart Dynamic Quests
            questRepository.clearAllQuests()
            
            val xpHistory = userRepository.getXPHistory().first()
            val weather = com.app.habittracker.domain.engines.WeatherType.values().random()
            
            // Calculate Performance (Last 3 days)
            val performance = if (xpHistory.isNotEmpty()) {
                val last3Days = xpHistory.filter { it.timestamp > System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000L) }
                val positive = last3Days.count { it.amount > 0 }
                val total = last3Days.size
                if (total > 0) positive.toFloat() / total.toFloat() else 0.5f
            } else 0.5f

            val smartQuests = smartQuestEngine.generateDynamicQuests(user, habits, performance, weather)
            smartQuests.forEach { questRepository.insertQuest(it) }
            
            // 4. Boss Evolution & Scaling
            val currentBoss = bossBattleRepository.getCurrentBossBattle().first()
            if (currentBoss == null || currentBoss.isDefeated) {
                val newBoss = smartQuestEngine.generateDynamicBoss(user, performance)
                bossBattleRepository.insertBossBattle(newBoss)
            } else if (System.currentTimeMillis() > currentBoss.endDate) {
                // Boss escaped! Apply penalty and replace
                gamificationEngine.awardXP(-100, "Boss Escaped: ${currentBoss.name}")
                val newBoss = smartQuestEngine.generateDynamicBoss(user, performance)
                bossBattleRepository.insertBossBattle(newBoss)
            }
            
            // 5. Recovery Quest logic
            val missedYesterday = habitsDueYesterday.filter { !it.isCompletedToday }
            val maxLostStreak = if (missedYesterday.isNotEmpty()) missedYesterday.maxOf { it.streak } else 0
            
            if (maxLostStreak >= 3) {
                val recoveryQuest = QuestEntity(
                    title = "Bounce Back Mission",
                    description = "Restore your $maxLostStreak day streak! Complete 2 habits today.",
                    xpReward = 200,
                    isRecovery = true,
                    lostStreak = maxLostStreak
                )
                questRepository.insertQuest(recoveryQuest)
            }
            
            // 6. Smart Reminders
            val bestHour = if (xpHistory.isNotEmpty()) {
                val hourCounts = xpHistory.groupingBy { 
                    val c = Calendar.getInstance()
                    c.timeInMillis = it.timestamp
                    c.get(Calendar.HOUR_OF_DAY)
                }.eachCount()
                hourCounts.maxByOrNull { it.value }?.key ?: 9
            } else {
                9
            }
            
            WorkScheduler.scheduleSmartReminder(context, bestHour)
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
