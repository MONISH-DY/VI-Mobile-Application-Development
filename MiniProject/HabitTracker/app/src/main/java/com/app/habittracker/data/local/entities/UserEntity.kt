package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String, // Unique UUID
    val phoneNumber: String,
    val pin: String, // 4-digit PIN
    val name: String,
    val totalXp: Int = 0,
    val currentLevel: Int = 1,
    val currentStreak: Int = 0,
    val highestStreak: Int = 0,
    val difficultyPreference: String = "Medium",
    val reminderTime: String = "08:00 AM",
    val lastHabitCompletionTime: Long = 0L,
    val activeXpBoostUntil: Long = 0L,
    val currentSparkySkin: String = "DEFAULT"
)
