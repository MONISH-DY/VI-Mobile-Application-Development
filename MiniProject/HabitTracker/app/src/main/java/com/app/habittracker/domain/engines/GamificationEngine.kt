package com.app.habittracker.domain.engines

import com.app.habittracker.data.local.entities.*
import com.app.habittracker.domain.repository.AchievementRepository
import com.app.habittracker.domain.repository.BossBattleRepository
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamificationEngine @Inject constructor(
    private val userRepository: UserRepository,
    private val achievementRepository: AchievementRepository,
    private val bossBattleRepository: BossBattleRepository,
    private val habitRepository: HabitRepository
) {
    companion object {
        const val XP_PER_LEVEL = 100
    }

    suspend fun calculateXPForHabit(habit: HabitEntity): Int {
        val baseXP = when (habit.difficulty) {
            "Hard" -> 40
            "Medium" -> 25
            else -> 10
        }

        val multiplier = calculateMultiplier(habit.streak)

        return (baseXP * multiplier).toInt()
    }

    private fun calculateMultiplier(streak: Int): Float {
        return when {
            streak >= 30 -> 2.0f // Optional extra multiplier for elite
            streak >= 7 -> 1.5f
            streak >= 3 -> 1.2f
            else -> 1.0f
        }
    }

    private val _levelChangeEvent = MutableSharedFlow<Pair<Int, Int>>(extraBufferCapacity = 1)
    val levelChangeEvent: SharedFlow<Pair<Int, Int>> = _levelChangeEvent.asSharedFlow()

    private val _lootEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val lootEvent: SharedFlow<String> = _lootEvent.asSharedFlow()

    private val _comboEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val comboEvent: SharedFlow<Unit> = _comboEvent.asSharedFlow()

    suspend fun calculateMasteryBuffs(): Float {
        val masteredHabits = habitRepository.getAllHabits().firstOrNull()?.filter { it.isMastered } ?: emptyList()
        var xpMultiplier = 0f
        masteredHabits.forEach { habit ->
            if (habit.category == "Work" || habit.category == "Productivity") {
                xpMultiplier += 0.05f // 5% bonus per mastered work habit
            }
        }
        return xpMultiplier
    }

    suspend fun awardXP(amount: Int, reason: String): Int {
        val user = userRepository.getUser().firstOrNull() ?: return 0
        
        var finalAmount = amount.toFloat()
        val now = System.currentTimeMillis()

        // Apply XP Boost if active
        if (now < user.activeXpBoostUntil) {
            finalAmount *= 2
        }

        // Apply Mastery Passives
        finalAmount *= (1f + calculateMasteryBuffs())

        // Apply Combo Multiplier (within 30 minutes)
        if (reason.startsWith("Completed:") && (now - user.lastHabitCompletionTime) < 30 * 60 * 1000L) {
            finalAmount *= 1.5f
            _comboEvent.tryEmit(Unit)
        }
        
        val xpChange = finalAmount.toInt()
        val newTotalXP = maxOf(0, user.totalXp + xpChange)
        val newLevel = (newTotalXP / XP_PER_LEVEL) + 1

        // Use atomic updates where possible
        userRepository.addXP(xpChange, reason)
        
        if (reason.startsWith("Completed:")) {
            userRepository.updateUser(user.copy(lastHabitCompletionTime = now))
        }

        if (newLevel != user.currentLevel) {
            userRepository.updateLevel(newLevel)
            _levelChangeEvent.tryEmit(user.currentLevel to newLevel)
            if (newLevel > user.currentLevel) {
                checkMilestoneBadges(newLevel)
                
                // Check if user CROSSED a multiple of 5
                val oldMilestone = user.currentLevel / 5
                val newMilestone = newLevel / 5
                if (newMilestone > oldMilestone) {
                    generateLoot()
                }
            }
        }
        return xpChange
    }

    private suspend fun generateLoot() {
        val lootPool = listOf("XP_BOOSTER", "RARE_SKIN", "MEGA_XP")
        val reward = lootPool.random()
        
        when (reward) {
            "XP_BOOSTER" -> {
                userRepository.updateXPBoost(System.currentTimeMillis() + 1 * 60 * 60 * 1000L)
                _lootEvent.tryEmit("XP_BOOSTER") // Trigger cinematic
            }
            "RARE_SKIN" -> {
                val skins = listOf("CYAN", "PINK", "GOLD", "RAINBOW")
                val skin = skins.random()
                userRepository.updateSkin(skin)
                _lootEvent.tryEmit("RARE_SKIN:$skin") // Trigger cinematic
            }
            "MEGA_XP" -> {
                awardXP(500, "Loot Reward: Mega XP")
                _lootEvent.tryEmit("MEGA_XP") // Trigger cinematic
            }
        }
    }

    suspend fun completeBossBattle(boss: BossBattleEntity) {
        awardXP(1000, "Defeated Boss: ${boss.name}")
        unlockAchievement("Boss Slayer")
    }

    private suspend fun checkMilestoneBadges(level: Int) {
        if (level >= 5) unlockAchievement("Level 5")
        if (level >= 10) unlockAchievement("Level 10")
        if (level >= 20) unlockAchievement("Level 20")
        if (level >= 50) unlockAchievement("Level 50")
    }

    suspend fun syncAchievements() {
        val user = userRepository.getUser().firstOrNull() ?: return
        checkMilestoneBadges(user.currentLevel)
    }

    suspend fun completeHabit(habit: HabitEntity): Int {
        // Check for Mastery (30 day streak)
        if (habit.streak >= 30 && !habit.isMastered) {
            habitRepository.updateHabit(habit.copy(isMastered = true))
            _lootEvent.tryEmit("Habit Mastered: ${habit.title}! Permanent Bonus Unlocked!")
        }

        val earnedXP = calculateXPForHabit(habit)
        val finalXP = awardXP(earnedXP, "Completed: ${habit.title}")
        
        unlockAchievement("First Habit")

        if (habit.streak == 7) {
            unlockAchievement("7 Day Streak")
        }

        if (habit.streak > 0 && habit.streak % 14 == 0) {
            _habitEvolutionEvent.tryEmit(habit)
        }

        if (habit.streak >= 30) {
            unlockAchievement("Elite Streak")
        }

        syncAchievements()
        return finalXP
    }

    private val _achievementUnlockedEvent = MutableSharedFlow<AchievementEntity>(extraBufferCapacity = 1)
    val achievementUnlockedEvent: SharedFlow<AchievementEntity> = _achievementUnlockedEvent.asSharedFlow()

    private val _habitEvolutionEvent = MutableSharedFlow<HabitEntity>(extraBufferCapacity = 1)
    val habitEvolutionEvent: SharedFlow<HabitEntity> = _habitEvolutionEvent.asSharedFlow()

    suspend fun unlockAchievement(badgeTitle: String): Boolean {
        val achievements = achievementRepository.getAllAchievements().firstOrNull() ?: return false
        val achievement = achievements.find { it.title == badgeTitle }
        
        if (achievement != null && !achievement.isUnlocked) {
            val updated = achievement.copy(isUnlocked = true, unlockedAt = System.currentTimeMillis())
            achievementRepository.updateAchievement(updated)
            _achievementUnlockedEvent.tryEmit(updated)
            return true
        }
        return false
    }
}
