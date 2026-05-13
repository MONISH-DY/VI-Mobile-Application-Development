package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "habits",
    indices = [
        androidx.room.Index(value = ["isCompletedToday"]),
        androidx.room.Index(value = ["isMissedToday"])
    ]
)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val title: String,
    val icon: String,
    val category: String,
    val difficulty: String, // Easy, Medium, Hard
    val frequency: String, // Daily, Weekly
    val preferredTime: String,
    val startTime: String = "09:00",
    val endTime: String = "21:00",
    val repeatDays: String = "Mon,Tue,Wed,Thu,Fri,Sat,Sun", // Comma separated days
    val targetDuration: Int = 0, // In minutes, 0 means no timer
    val notes: String,
    val isCompletedToday: Boolean = false,
    val isMissedToday: Boolean = false,
    val streak: Int = 0,
    val isMastered: Boolean = false,
    val lastNotificationDate: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)
