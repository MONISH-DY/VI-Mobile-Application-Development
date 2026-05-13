package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val title: String,
    val description: String,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val isRecovery: Boolean = false,
    val lostStreak: Int = 0,
    val startTime: String = "00:00",
    val endTime: String = "23:59",
    val targetDuration: Int = 0,
    val isMissed: Boolean = false,
    val isIncremental: Boolean = false,
    val currentProgress: Int = 0,
    val targetProgress: Int = 1,
    val generatedDate: Long = System.currentTimeMillis()
)
