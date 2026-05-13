package com.app.habittracker.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.domain.repository.QuestRepository
import com.app.habittracker.domain.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull

@HiltWorker
class SmartReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository,
    private val questRepository: QuestRepository,
    private val userRepository: UserRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val notificationHelper = NotificationHelper(context)
            
            // 1. Analyze State
            val user = userRepository.getUser().firstOrNull() ?: return Result.success()
            val missedHabits = habitRepository.getMissedHabits()
            val activeQuests = questRepository.getActiveQuests().firstOrNull() ?: emptyList()
            val hasRecoveryQuest = activeQuests.any { it.isRecovery }

            // 2. Determine Reminder Type
            val title: String
            val message: String

            when {
                hasRecoveryQuest -> {
                    title = "🦅 Comeback King"
                    message = "Your Bounce Back Mission is active. Complete 2 habits today to restore your streak!"
                }
                missedHabits.isNotEmpty() -> {
                    title = "⚠️ Don't Break the Chain"
                    message = "You missed ${missedHabits.size} habits yesterday. Let's get back on track today!"
                }
                user.currentStreak >= 3 -> {
                    title = "🔥 Unstoppable"
                    message = "You're on a ${user.currentStreak}-day streak! Keep the momentum going!"
                }
                else -> {
                    title = "🎯 Daily Quest Reminder"
                    message = "Your daily habits are waiting. Time to level up!"
                }
            }

            // 3. Send Notification
            notificationHelper.showReminder(title, message)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
