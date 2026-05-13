package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val title: String,
    val message: String,
    val scheduledTime: Long,
    val type: String = "Normal",
    val isSent: Boolean = false
)
