package com.app.habittracker.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.habittracker.domain.engines.GamificationEngine
import com.app.habittracker.domain.repository.HabitRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HabitActionReceiver : BroadcastReceiver() {
    @Inject
    lateinit var habitRepository: HabitRepository
    @Inject
    lateinit var gamificationEngine: GamificationEngine

    override fun onReceive(context: Context, intent: Intent) {
        val habitId = intent.getIntOfExtra("HABIT_ID", -1)
        if (habitId == -1) return

        val action = intent.action
        if (action == "ACTION_DONE") {
            CoroutineScope(Dispatchers.IO).launch {
                val habits = habitRepository.getAllHabits().first()
                habits.find { it.id == habitId }?.let { habit ->
                    if (!habit.isCompletedToday) {
                        val updatedHabit = habit.copy(isCompletedToday = true, streak = habit.streak + 1)
                        habitRepository.updateHabit(updatedHabit)
                        
                        val calendar = java.util.Calendar.getInstance()
                        val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH).format(calendar.time)
                        habitRepository.logHabitCompletion(habit.id, dateStr, true)
                        
                        gamificationEngine.completeHabit(updatedHabit)
                        
                        // Dismiss the notification
                        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                        manager.cancel(habitId)
                    }
                }
            }
        } else if (action == "ACTION_SNOOZE") {
            // Dismiss current
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            manager.cancel(habitId)
            
            // Schedule one-time reminder in 1 hour
            WorkScheduler.scheduleSnoozedReminder(context, habitId)
        }
    }
}

private fun Intent.getIntOfExtra(name: String, defaultValue: Int): Int {
    return if (hasExtra(name)) getIntExtra(name, defaultValue) else defaultValue
}
