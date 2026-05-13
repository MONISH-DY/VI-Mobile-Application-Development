package com.app.habittracker.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.habittracker.domain.engines.GamificationEngine
import com.app.habittracker.domain.repository.HabitRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class HabitStatusWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository,
    private val gamificationEngine: GamificationEngine
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val calendar = Calendar.getInstance()
            val currentDay = SimpleDateFormat("EEE", Locale.ENGLISH).format(calendar.time)
            val currentTime = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(calendar.time)
            val todayStr = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(calendar.time)

            val notificationHelper = NotificationHelper(context)
            val habits = habitRepository.getAllHabits().first()

            habits.forEach { habit ->
                if (habit.repeatDays.contains(currentDay)) {
                    val lastNotifDateStr = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date(habit.lastNotificationDate))
                    
                    val start24 = com.app.habittracker.util.TimeUtils.formatTo24h(habit.startTime)
                    val end24 = com.app.habittracker.util.TimeUtils.formatTo24h(habit.endTime)

                    // Start notification
                    if (currentTime >= start24 && currentTime <= end24) {
                        if (lastNotifDateStr != todayStr && !habit.isCompletedToday) {
                            notificationHelper.showReminder(
                                "Habit Active: ${habit.title}",
                                "Your ${habit.category} habit is now active until ${habit.endTime}. Time to crush it!",
                                notificationId = habit.id
                            )
                            habitRepository.updateHabit(habit.copy(lastNotificationDate = System.currentTimeMillis()))
                        }
                    }

                    // End time penalty (if not already handled)
                    if (currentTime > end24 && !habit.isCompletedToday && !habit.isMissedToday) {
                        val basePenalty = gamificationEngine.calculateXPForHabit(habit)
                        val finalPenalty = maxOf(25, basePenalty)
                        gamificationEngine.awardXP(-finalPenalty, "Missed: ${habit.title}")
                        habitRepository.updateHabit(habit.copy(isMissedToday = true, streak = 0))
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
