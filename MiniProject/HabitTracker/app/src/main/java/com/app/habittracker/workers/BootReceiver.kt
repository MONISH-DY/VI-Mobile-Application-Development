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
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var habitRepository: HabitRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            WorkScheduler.scheduleDailyReset(context)
            WorkScheduler.scheduleHabitStatusCheck(context)
            
            // Re-schedule all exact alarms
            CoroutineScope(Dispatchers.IO).launch {
                val habits = habitRepository.getAllHabits().first()
                habits.forEach { habit ->
                    if (!habit.isCompletedToday) {
                        WorkScheduler.scheduleExactAlarm(context, habit)
                    }
                }
            }
        }
    }
}
