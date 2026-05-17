package com.app.habittracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.data.local.entities.UserEntity
import com.app.habittracker.domain.engines.GamificationEngine
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.app.habittracker.data.local.entities.BossBattleEntity
import com.app.habittracker.data.local.entities.QuestEntity
import com.app.habittracker.domain.repository.BossBattleRepository
import com.app.habittracker.domain.repository.QuestRepository
import com.app.habittracker.data.local.PreferencesManager

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val habitRepository: HabitRepository,
    private val gamificationEngine: GamificationEngine,
    private val questRepository: QuestRepository,
    private val bossBattleRepository: BossBattleRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _dismissedHabitIds = MutableStateFlow<Set<Int>>(emptySet())

    val uiState: StateFlow<DashboardUiState> = combine(
        userRepository.getUser(),
        habitRepository.getAllHabits(),
        questRepository.getAllQuests(),
        bossBattleRepository.getCurrentBossBattle(),
        preferencesManager.isDarkMode.combine(_dismissedHabitIds) { isDark, ids -> isDark to ids }
    ) { user, habits, quests, boss, settings ->
        val (isDark, dismissedIds) = settings
        if (user != null) {
            val calendar = java.util.Calendar.getInstance()
            val currentDay = java.text.SimpleDateFormat("EEE", java.util.Locale.ENGLISH).format(calendar.time)
            val currentTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH).format(calendar.time)

            // Filter habits due today AND not dismissed
            val activeHabits = habits.filter { habit ->
                habit.repeatDays.contains(currentDay) && !dismissedIds.contains(habit.id)
            }.sortedBy { habit ->
                val start24 = com.app.habittracker.util.TimeUtils.formatTo24h(habit.startTime)
                val end24 = com.app.habittracker.util.TimeUtils.formatTo24h(habit.endTime)
                
                when {
                    habit.isCompletedToday -> 3
                    currentTime in start24..end24 -> 0 // Active
                    currentTime < start24 -> 1 // Upcoming
                    else -> 2 // Missed
                }
            }

            DashboardUiState.Success(
                user = user,
                pendingHabits = activeHabits,
                activeQuests = quests,
                currentBoss = boss,
                isDarkMode = isDark
            )
        } else {
            DashboardUiState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )

    fun toggleTheme(currentIsDark: Boolean) {
        viewModelScope.launch {
            preferencesManager.saveIsDarkMode(!currentIsDark)
        }
    }

    private val _xpEarnedEvent = MutableStateFlow<Int?>(null)
    val xpEarnedEvent: StateFlow<Int?> = _xpEarnedEvent.asStateFlow()

    private val _levelUpEvent = MutableStateFlow<Int?>(null)
    val levelUpEvent: StateFlow<Int?> = _levelUpEvent.asStateFlow()

    private val _achievementUnlockedEvent = MutableStateFlow<com.app.habittracker.data.local.entities.AchievementEntity?>(null)
    val achievementUnlockedEvent: StateFlow<com.app.habittracker.data.local.entities.AchievementEntity?> = _achievementUnlockedEvent.asStateFlow()

    private val _habitEvolutionEvent = MutableStateFlow<HabitEntity?>(null)
    val habitEvolutionEvent: StateFlow<HabitEntity?> = _habitEvolutionEvent.asStateFlow()

    private val _comboEvent = MutableStateFlow<Boolean>(false)
    val comboEvent: StateFlow<Boolean> = _comboEvent.asStateFlow()

    private val _lootMessage = MutableStateFlow<String?>(null)
    val lootMessage: StateFlow<String?> = _lootMessage.asStateFlow()

    private val _levelDownEvent = MutableStateFlow<Int?>(null)
    val levelDownEvent: StateFlow<Int?> = _levelDownEvent.asStateFlow()

    init {
        checkExpirations()
        // Automatic ticker to check for missed habits every 30 seconds
        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(30000)
                checkExpirations()
            }
        }
        viewModelScope.launch {
            gamificationEngine.syncAchievements()
        }
        viewModelScope.launch {
            gamificationEngine.levelChangeEvent.collect { (oldLevel, newLevel) ->
                if (newLevel > oldLevel) {
                    _levelUpEvent.value = newLevel
                    kotlinx.coroutines.delay(5000)
                    _levelUpEvent.value = null
                } else if (newLevel < oldLevel) {
                    _levelDownEvent.value = newLevel
                    kotlinx.coroutines.delay(5000)
                    _levelDownEvent.value = null
                }
            }
        }
        viewModelScope.launch {
            gamificationEngine.achievementUnlockedEvent.collect { achievement ->
                _achievementUnlockedEvent.value = achievement
                kotlinx.coroutines.delay(4000)
                _achievementUnlockedEvent.value = null
            }
        }
        viewModelScope.launch {
            gamificationEngine.habitEvolutionEvent.collect { habit ->
                _habitEvolutionEvent.value = habit
            }
        }
        viewModelScope.launch {
            gamificationEngine.comboEvent.collect {
                _comboEvent.value = true
                kotlinx.coroutines.delay(3000)
                _comboEvent.value = false
            }
        }
        viewModelScope.launch {
            gamificationEngine.lootEvent.collect { message ->
                _lootMessage.value = message
                kotlinx.coroutines.delay(6000)
                _lootMessage.value = null
            }
        }
    }
    fun dismissEvolution() {
        _habitEvolutionEvent.value = null
    }

    fun dismissLoot() {
        _lootMessage.value = null
    }

    fun dismissLevelUp() {
        _levelUpEvent.value = null
    }

    fun dismissLevelDown() {
        _levelDownEvent.value = null
    }

    fun evolveHabit(habit: HabitEntity) {
        viewModelScope.launch {
            // RESTORED detailed evolution logic
            var newTitle = habit.title
            newTitle = if (newTitle.contains("10 min")) newTitle.replace("10 min", "20 min")
            else if (newTitle.contains("20 min")) newTitle.replace("20 min", "30 min")
            else if (newTitle.contains("5k")) newTitle.replace("5k", "7k")
            else if (newTitle.contains("7k")) newTitle.replace("7k", "10k")
            else if (!newTitle.contains("+")) "$newTitle +"
            else "$newTitle+"

            val upgradedHabit = habit.copy(
                title = newTitle,
                difficulty = "Hard"
            )
            habitRepository.updateHabit(upgradedHabit)
            gamificationEngine.awardXP(100, "Habit Evolved!")
            _habitEvolutionEvent.value = null
        }
    }

    fun refreshDashboard() {
        viewModelScope.launch {
            checkExpirations()
            
            // RESTORED dismiss logic
            val habits = habitRepository.getAllHabits().first()
            val toDismiss = habits.filter { habit ->
                habit.isCompletedToday || habit.isMissedToday
            }.map { it.id }.toSet()
            
            _dismissedHabitIds.value += toDismiss
        }
    }

    fun completeHabit(habit: HabitEntity) {
        viewModelScope.launch {
            val updatedHabit = habit.copy(isCompletedToday = true, streak = habit.streak + 1)
            habitRepository.updateHabit(updatedHabit)
            
            val dateStr = com.app.habittracker.util.TimeUtils.getUtcDateString()
            habitRepository.logHabitCompletion(habit.id, dateStr, true)
            
            val xp = gamificationEngine.completeHabit(updatedHabit)
            
            _xpEarnedEvent.value = xp
            
            // Deduct HP from Boss
            bossBattleRepository.getCurrentBossBattle().first().let { boss ->
                if (boss != null && !boss.isDefeated) {
                    val baseDamage = when (habit.difficulty) {
                        "Hard" -> 50
                        "Medium" -> 25
                        else -> 10
                    }
                    // If boss is enraged, it might take LESS damage or deal more penalty.
                    // For now, let's keep damage normal but increase XP penalty if missed.
                    bossBattleRepository.updateBossBattle(boss.copy(currentHp = maxOf(0, boss.currentHp - baseDamage)))
                }
            }

            kotlinx.coroutines.delay(2000)
            _xpEarnedEvent.value = null
        }
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            if (!habit.isMissedToday && !habit.isCompletedToday) {
                val boss = bossBattleRepository.getCurrentBossBattle().first()
                val multiplier = if (boss?.isEnraged == true) 2 else 1
                gamificationEngine.awardXP(-25 * multiplier, "Giving Up: ${habit.title}")
            }
            habitRepository.deleteHabit(habit)
            questRepository.deleteQuestByTitle("Daily: ${habit.title}")
        }
    }

    private fun checkExpirations() {
        viewModelScope.launch {
            val calendar = java.util.Calendar.getInstance()
            val currentDay = java.text.SimpleDateFormat("EEE", java.util.Locale.ENGLISH).format(calendar.time)
            val currentTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH).format(calendar.time)

            // Optimized: only fetch habits that could potentially be expired
            val habitsToCheck = habitRepository.getUnprocessedHabitsForDay(currentDay)
            
            if (habitsToCheck.isNotEmpty()) {
                val boss = bossBattleRepository.getCurrentBossBattle().first()
                
                for (habit in habitsToCheck) {
                    val end24 = com.app.habittracker.util.TimeUtils.formatTo24h(habit.endTime)
                    val start24 = com.app.habittracker.util.TimeUtils.formatTo24h(habit.startTime)
                    
                    val isRangeCrossesMidnight = start24 > end24
                    if (!isRangeCrossesMidnight && currentTime > end24) {
                        // Penalty!
                        val basePenalty = gamificationEngine.calculateXPForHabit(habit)
                        val multiplier = if (boss?.isEnraged == true) 2 else 1
                        val finalPenalty = maxOf(25, basePenalty) * multiplier
                        
                        gamificationEngine.awardXP(-finalPenalty, "Missed: ${habit.title}${if (multiplier > 1) " (ENRAGED BOSS)" else ""}")
                        habitRepository.updateHabit(habit.copy(isMissedToday = true, streak = 0))
                        
                        val dateStr = com.app.habittracker.util.TimeUtils.getUtcDateString()
                        habitRepository.logHabitCompletion(habit.id, dateStr, false)
                        
                        // If user misses a habit and a boss is active, it becomes enraged!
                        if (boss != null && !boss.isDefeated && !boss.isEnraged) {
                            bossBattleRepository.updateBossBattle(boss.copy(isEnraged = true))
                        }
                    }
                }
            }
        }
    }
}

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(
        val user: UserEntity,
        val pendingHabits: List<HabitEntity>,
        val activeQuests: List<QuestEntity>,
        val currentBoss: BossBattleEntity?,
        val isDarkMode: Boolean
    ) : DashboardUiState
}
