package com.app.habittracker.workers

import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object WorkScheduler {
    fun scheduleDailyReset(context: Context) {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (before(currentDate)) {
                add(Calendar.HOUR_OF_DAY, 24)
            }
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyResetWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_reset_work",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }

    fun scheduleSmartReminder(context: Context, bestHourOfDay: Int) {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, bestHourOfDay)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (before(currentDate)) {
                add(Calendar.HOUR_OF_DAY, 24)
            }
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val reminderWorkRequest = androidx.work.OneTimeWorkRequestBuilder<SmartReminderWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "smart_reminder_work",
            androidx.work.ExistingWorkPolicy.REPLACE,
            reminderWorkRequest
        )
    }

    fun scheduleHabitStatusCheck(context: Context) {
        val statusWorkRequest = PeriodicWorkRequestBuilder<HabitStatusWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "habit_status_work",
            ExistingPeriodicWorkPolicy.KEEP,
            statusWorkRequest
        )
    }

    fun scheduleSnoozedReminder(context: Context, habitId: Int) {
        val snoozeRequest = androidx.work.OneTimeWorkRequestBuilder<SnoozeWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .setInputData(androidx.work.workDataOf("HABIT_ID" to habitId))
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "snooze_habit_$habitId",
            androidx.work.ExistingWorkPolicy.REPLACE,
            snoozeRequest
        )
    }

    fun scheduleExactAlarm(context: Context, habit: com.app.habittracker.data.local.entities.HabitEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
        
        // Parse start time (e.g., "09:00 AM")
        val timeParts = habit.startTime.split(" ", ":")
        var hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()
        val amPm = if (timeParts.size > 2) timeParts[2] else "AM"
        
        if (amPm == "PM" && hour < 12) hour += 12
        if (amPm == "AM" && hour == 12) hour = 0

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intent = Intent(context, HabitReminderReceiver::class.java).apply {
            putExtra("HABIT_ID", habit.id)
        }
        val pendingIntent = android.app.PendingIntent.getBroadcast(
            context, habit.id, intent,
            android.app.PendingIntent.FLAG_IMMUTABLE or android.app.PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    android.app.AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    android.app.AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                android.app.AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}
