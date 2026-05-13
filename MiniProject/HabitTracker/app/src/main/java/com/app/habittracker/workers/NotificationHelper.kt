package com.app.habittracker.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.app.habittracker.MainActivity

class NotificationHelper(private val context: Context) {
    companion object {
        const val CHANNEL_ID = "smart_reminders_channel"
        const val CHANNEL_NAME = "Smart Reminders"
        const val NOTIFICATION_ID = 1001
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders for your daily habits and quests"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showReminder(title: String, message: String, notificationId: Int = NOTIFICATION_ID) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Action: Mark as Done
        val doneIntent = Intent(context, HabitActionReceiver::class.java).apply {
            action = "ACTION_DONE"
            putExtra("HABIT_ID", notificationId) // Use the same ID
        }
        val donePendingIntent = PendingIntent.getBroadcast(
            context, notificationId, doneIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Action: Snooze
        val snoozeIntent = Intent(context, HabitActionReceiver::class.java).apply {
            action = "ACTION_SNOOZE"
            putExtra("HABIT_ID", notificationId)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context, notificationId + 10000, snoozeIntent, // Unique requestCode
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_menu_edit, "Mark as Done", donePendingIntent)
            .addAction(android.R.drawable.ic_lock_idle_alarm, "Snooze (1h)", snoozePendingIntent)

        notificationManager.notify(notificationId, builder.build())
    }
}
