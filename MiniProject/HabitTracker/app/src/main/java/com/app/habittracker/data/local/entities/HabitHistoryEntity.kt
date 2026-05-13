package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "habit_history",
    indices = [
        Index(value = ["habitId"]),
        Index(value = ["dateStr"])
    ]
)
data class HabitHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val habitId: Int,
    val dateStr: String, // Format: YYYY-MM-DD
    val isCompleted: Boolean
)
