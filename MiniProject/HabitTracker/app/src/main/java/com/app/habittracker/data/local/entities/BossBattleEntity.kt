package com.app.habittracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boss_battles")
data class BossBattleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val name: String,
    val description: String,
    val task1Name: String,
    val task1Desc: String,
    val task1Completed: Boolean = false,
    val task1StartTime: String = "00:00",
    val task1EndTime: String = "23:59",
    val task1Duration: Int = 0,
    val task2Name: String,
    val task2Desc: String,
    val task2Completed: Boolean = false,
    val task2StartTime: String = "00:00",
    val task2EndTime: String = "23:59",
    val task2Duration: Int = 0,
    val task3Name: String,
    val task3Desc: String,
    val task3Completed: Boolean = false,
    val task3StartTime: String = "00:00",
    val task3EndTime: String = "23:59",
    val task3Duration: Int = 0,
    val currentHp: Int = 1000,
    val maxHp: Int = 1000,
    val startDate: Long,
    val endDate: Long,
    val isDefeated: Boolean = false,
    val isEnraged: Boolean = false, // Double damage if true
    val rewardXp: Int
)
