package com.app.habittracker.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.data.local.entities.HabitHistoryEntity
import com.app.habittracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    val allHistory: StateFlow<List<HabitHistoryEntity>> = habitRepository.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val historyByDate: StateFlow<Map<String, List<HabitHistoryEntity>>> = allHistory.map { list ->
        list.groupBy { it.dateStr }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val allHabits: StateFlow<List<HabitEntity>> = habitRepository.getAllHabits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyPerformance: StateFlow<Map<String, Float>> = historyByDate.map { map ->
        map.mapValues { entry ->
            val total = entry.value.size
            val completed = entry.value.count { it.isCompleted }
            if (total == 0) 0f else completed.toFloat() / total.toFloat()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
}
