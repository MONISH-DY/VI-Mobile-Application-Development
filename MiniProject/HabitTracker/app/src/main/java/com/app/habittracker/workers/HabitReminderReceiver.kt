package com.app.habittracker.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.habittracker.domain.repository.HabitRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HabitReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var habitRepository: HabitRepository

    override fun onReceive(context: Context, intent: Intent) {
        val habitId = intent.getIntExtra("HABIT_ID", -1)
        if (habitId == -1) return

        CoroutineScope(Dispatchers.IO).launch {
            val habits = habitRepository.getAllHabits().first()
            val habit = habits.find { it.id == habitId } ?: return@launch

            if (!habit.isCompletedToday) {
                val notificationHelper = NotificationHelper(context)
                notificationHelper.showReminder(
                    "Time for: ${habit.title}",
                    "Your ${habit.category} habit is starting now! Ready to level up?",
                    notificationId = habit.id
                )
            }
            
            // Re-schedule for tomorrow
            WorkScheduler.scheduleExactAlarm(context, habit)
        }
    }
}
