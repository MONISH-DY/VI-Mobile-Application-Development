package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xp_history")
data class XPHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val amount: Int,
    val reason: String,
    val timestamp: Long = System.currentTimeMillis()
)
