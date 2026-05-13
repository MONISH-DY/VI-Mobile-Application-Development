package com.app.habittracker.presentation.bossbattle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.BossBattleEntity
import com.app.habittracker.domain.engines.GamificationEngine
import com.app.habittracker.domain.repository.BossBattleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossBattleViewModel @Inject constructor(
    private val bossBattleRepository: BossBattleRepository,
    private val gamificationEngine: GamificationEngine,
    private val smartQuestEngine: com.app.habittracker.domain.engines.SmartQuestEngine,
    private val userRepository: com.app.habittracker.domain.repository.UserRepository
) : ViewModel() {

    val currentBoss: StateFlow<BossBattleEntity?> = bossBattleRepository.getCurrentBossBattle()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        checkAndGenerateWeeklyBoss()
    }

    private fun checkAndGenerateWeeklyBoss() {
        viewModelScope.launch {
            val currentBoss = bossBattleRepository.getCurrentBossBattle().first()
            
            val cal = java.util.Calendar.getInstance()
            cal.firstDayOfWeek = java.util.Calendar.SUNDAY
            cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
            cal.set(java.util.Calendar.MINUTE, 0)
            cal.set(java.util.Calendar.SECOND, 0)
            cal.set(java.util.Calendar.MILLISECOND, 0)
            
            while (cal.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.SUNDAY) {
                cal.add(java.util.Calendar.DAY_OF_MONTH, -1)
            }
            val recentSundayStart = cal.timeInMillis
            val nextSundayStart = recentSundayStart + 7 * 24 * 60 * 60 * 1000L
            
            // Check if current boss is from an older week or if we need a new one
            if (currentBoss == null || currentBoss.startDate < recentSundayStart) {
                val user = userRepository.getUser().firstOrNull() ?: return@launch
                val newBoss = smartQuestEngine.generateDynamicBoss(user, 0.5f) // Default performance for boss
                
                // Adjust boss for weekly timeframe
                val weeklyBoss = newBoss.copy(
                    startDate = recentSundayStart,
                    endDate = nextSundayStart
                )
                bossBattleRepository.insertBossBattle(weeklyBoss)
            }
        }
    }

    fun completeBossTask(taskIndex: Int) {
        viewModelScope.launch {
            val boss = currentBoss.value ?: return@launch
            if (boss.isDefeated) return@launch
            
            var t1 = boss.task1Completed
            var t2 = boss.task2Completed
            var t3 = boss.task3Completed
            
            when (taskIndex) {
                1 -> if (t1) return@launch else t1 = true
                2 -> if (t2) return@launch else t2 = true
                3 -> if (t3) return@launch else t3 = true
            }
            
            val isDefeated = t1 && t2 && t3
            val newHp = if (isDefeated) 0 else (boss.currentHp - 334).coerceAtLeast(1)
            
            val updatedBoss = boss.copy(
                task1Completed = t1,
                task2Completed = t2,
                task3Completed = t3,
                currentHp = newHp,
                isDefeated = isDefeated
            )
            if (isDefeated) {
                gamificationEngine.completeBossBattle(updatedBoss)
            }
            bossBattleRepository.updateBossBattle(updatedBoss)
        }
    }
}
