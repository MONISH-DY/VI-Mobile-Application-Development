package com.app.habittracker.presentation.quests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.QuestEntity
import com.app.habittracker.domain.engines.GamificationEngine
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.domain.repository.QuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyQuestsViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val habitRepository: HabitRepository,
    private val gamificationEngine: GamificationEngine
) : ViewModel() {

    val allQuests: StateFlow<List<QuestEntity>> = questRepository.getAllQuests()
        .map { quests ->
            if (quests.isEmpty()) return@map emptyList<QuestEntity>()
            
            val calendar = java.util.Calendar.getInstance()
            val currentTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH).format(calendar.time)
            
            quests.map { quest ->
                if (!quest.isCompleted && !quest.isMissed && quest.endTime.isNotEmpty() && currentTime > quest.endTime) {
                    quest.copy(isMissed = true)
                } else {
                    quest
                }
            }.sortedByDescending { it.id }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        checkAndGenerateDailyQuests()
        checkExpirations()
    }

    @Inject lateinit var smartQuestEngine: com.app.habittracker.domain.engines.SmartQuestEngine
    @Inject lateinit var userRepository: com.app.habittracker.domain.repository.UserRepository

    private fun checkAndGenerateDailyQuests() {
        viewModelScope.launch {
            val currentQuests = questRepository.getAllQuests().first()
            
            val todayStart = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, 0)
                set(java.util.Calendar.MINUTE, 0)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }.timeInMillis
            
            val hasOldQuests = currentQuests.any { it.generatedDate < todayStart }
            
            if (currentQuests.isEmpty() || hasOldQuests) {
                if (hasOldQuests) {
                    questRepository.clearAllQuests()
                }
                
                val user = userRepository.getUser().first()!!
                val habits = habitRepository.getAllHabits().first()

                
                // Use a neutral 0.5 performance if not enough data
                val smartQuests = smartQuestEngine.generateDynamicQuests(user, habits, 0.5f, com.app.habittracker.domain.engines.WeatherType.SUNNY)
                smartQuests.forEach { questRepository.insertQuest(it) }
            }
        }
    }

    fun claimQuestReward(quest: QuestEntity) {
        viewModelScope.launch {
            if (quest.isCompleted) return@launch
            
            val updatedQuest = quest.copy(isCompleted = true)
            questRepository.updateQuest(updatedQuest)
            gamificationEngine.awardXP(quest.xpReward, "Quest Completed: ${quest.title}")

            if (quest.isRecovery) {
                // Restore partial streak (e.g., half of what was lost)
                val restoredAmount = maxOf(1, quest.lostStreak / 2)
                habitRepository.restoreHabitStreaks(restoredAmount)
                gamificationEngine.unlockAchievement("Comeback King")
            }

            // Check if all regular daily quests are completed
            val remainingIncomplete = questRepository.getAllQuests().first().filter { 
                !it.isRecovery && !it.isCompleted && !it.isMissed
            }

            if (remainingIncomplete.isEmpty()) {
                gamificationEngine.awardXP(100, "Daily Quests Cleared Bonus!")
                gamificationEngine.unlockAchievement("Perfect Week")
            }
        }
    }

    fun incrementQuestProgress(quest: QuestEntity) {
        viewModelScope.launch {
            if (quest.isCompleted || quest.isMissed) return@launch
            
            val newProgress = quest.currentProgress + 1
            val isFinished = newProgress >= quest.targetProgress
            
            val updatedQuest = quest.copy(
                currentProgress = newProgress,
                isCompleted = isFinished
            )
            questRepository.updateQuest(updatedQuest)
            
            if (isFinished) {
                gamificationEngine.awardXP(quest.xpReward, "Quest Completed: ${quest.title}")
            }
        }
    }

    private fun checkExpirations() {
        viewModelScope.launch {
            val calendar = java.util.Calendar.getInstance()
            val currentTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH).format(calendar.time)

            val quests = questRepository.getAllQuests().first()
            quests.forEach { quest ->
                if (!quest.isCompleted && !quest.isMissed && currentTime > quest.endTime) {
                    // Mark as missed (optional, no penalty)
                    questRepository.updateQuest(quest.copy(isMissed = true))
                }
            }
        }
    }
}
