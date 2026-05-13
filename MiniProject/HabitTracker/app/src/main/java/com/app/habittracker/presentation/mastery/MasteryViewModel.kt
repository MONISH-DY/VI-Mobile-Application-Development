package com.app.habittracker.presentation.mastery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.HabitEntity
import com.app.habittracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MasteryViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    val masteredHabits: StateFlow<List<HabitEntity>> = habitRepository.getAllHabits()
        .map { habits ->
            habits.filter { it.isMastered || it.streak >= 30 }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
