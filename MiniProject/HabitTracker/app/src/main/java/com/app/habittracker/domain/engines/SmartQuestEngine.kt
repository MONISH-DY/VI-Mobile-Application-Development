package com.app.habittracker.domain.engines

import com.app.habittracker.data.local.entities.BossBattleEntity
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.data.local.entities.QuestEntity
import com.app.habittracker.data.local.entities.UserEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartQuestEngine @Inject constructor() {

    private val random = Random()

    fun generateDynamicQuests(user: UserEntity, habits: List<HabitEntity>, performance: Float, weather: WeatherType): List<QuestEntity> {
        val quests = mutableListOf<QuestEntity>()
        val isToughMode = performance > 0.8f
        
        // 1. Prioritize Habit-based Quests
        val habitQuests = habits.shuffled().take(2).map { habit ->
            QuestEntity(
                title = "Daily: ${habit.title}",
                description = "Master your ${habit.title} habit today for bonus XP!",
                xpReward = if (isToughMode) 75 else 50,
                startTime = "00:00",
                endTime = if (habit.preferredTime.contains("PM")) "23:59" else "18:00",
                targetDuration = habit.targetDuration
            )
        }
        quests.addAll(habitQuests)

        // 2. Fill the rest with Archetype Quests to reach exactly 3
        val archetypes = listOf("Speedster", "Zen Master", "Scholar", "Athlete").shuffled()
        var archIndex = 0
        while (quests.size < 3) {
            quests.add(generateArchetypeQuest(archetypes[archIndex % archetypes.size], isToughMode, weather))
            archIndex++
        }

        return quests.take(3)
    }

    private fun generateArchetypeQuest(archetype: String, tough: Boolean, weather: WeatherType): QuestEntity {
        return when (archetype) {
            "Speedster" -> QuestEntity(
                title = "Blitz Mode", 
                description = "Finish 2 habits as fast as possible!", 
                xpReward = if (tough) 120 else 60,
                startTime = "00:00",
                endTime = "12:00"
            )
            "Zen Master" -> QuestEntity(
                title = "Inner Peace", 
                description = "Focus for 20+ mins during your meditation.", 
                xpReward = if (tough) 150 else 75,
                startTime = "00:00",
                endTime = "23:59"
            )
            else -> QuestEntity(
                title = "Muscle Memory", 
                description = "Complete all physical habits.", 
                xpReward = 200,
                startTime = "00:00",
                endTime = "21:00"
            )
        }
    }

    fun generateDynamicBoss(user: UserEntity, performance: Float): BossBattleEntity {
        val bossName = listOf("Goliath Titan", "Shadow Ninja", "Enigma Mage").random()
        val baseHp = 500 + (user.currentLevel * 50)
        val finalHp = (baseHp * (1.5f - performance)).toInt()
        
        val tasks = when (bossName) {
            "Shadow Ninja" -> Triple("Morning Blitz", "Ghost Mode", "Fast Finish")
            "Goliath Titan" -> Triple("Heavy Lifting", "Iron Will", "Titan Slayer")
            else -> Triple("Deep Focus", "Mental Fortress", "Zen Master")
        }

        // Toughness-based durations (Strictly 10-20 mins with random variation)
        val (d1, d2, d3) = when(bossName) {
            "Goliath Titan" -> Triple(12 + random.nextInt(3), 16 + random.nextInt(3), 18 + random.nextInt(3))
            "Shadow Ninja" -> Triple(10 + random.nextInt(2), 12 + random.nextInt(3), 14 + random.nextInt(4))
            else -> Triple(11 + random.nextInt(3), 14 + random.nextInt(3), 16 + random.nextInt(4))
        }
        
        // Final clamp to ensure 10-20 range
        val fd1 = d1.coerceIn(10, 20)
        val fd2 = d2.coerceIn(10, 20)
        val fd3 = d3.coerceIn(10, 20)

        return BossBattleEntity(
            name = bossName,
            description = "Defeat the $bossName to earn massive rewards!",
            maxHp = finalHp,
            currentHp = finalHp,
            rewardXp = 500 + (user.currentLevel * 20),
            task1Name = tasks.first,
            task1Desc = "Legendary challenge: Complete within $fd1 mins",
            task1Duration = fd1,
            task2Name = tasks.second,
            task2Desc = "Legendary challenge: Complete within $fd2 mins",
            task2Duration = fd2,
            task3Name = tasks.third,
            task3Desc = "Legendary challenge: Complete within $fd3 mins",
            task3Duration = fd3,
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + (24 * 60 * 60 * 1000L),
            isDefeated = false
        )
    }
}

enum class WeatherType {
    SUNNY, RAINY, CLOUDY
}
