package com.app.habittracker.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.habittracker.domain.repository.HabitRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class SnoozeWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val habitId = inputData.getInt("HABIT_ID", -1)
        if (habitId == -1) return Result.failure()

        val habits = habitRepository.getAllHabits().first()
        val habit = habits.find { it.id == habitId } ?: return Result.success()

        if (!habit.isCompletedToday) {
            val notificationHelper = NotificationHelper(context)
            notificationHelper.showReminder(
                "Snoozed: ${habit.title}",
                "Time's up! Ready to complete your ${habit.category} habit now?",
                notificationId = habitId
            )
        }

        return Result.success()
    }
}
