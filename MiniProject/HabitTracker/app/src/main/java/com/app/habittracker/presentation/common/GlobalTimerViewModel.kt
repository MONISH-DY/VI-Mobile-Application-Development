package com.app.habittracker.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalTimerViewModel @Inject constructor(
    private val timerManager: com.app.habittracker.domain.engines.TimerManager
) : ViewModel() {

    val activeTimerTitle: StateFlow<String?> = timerManager.activeTimerTitle
    val timerRemainingSeconds: StateFlow<Int> = timerManager.timerRemainingSeconds
    val isPaused: StateFlow<Boolean> = timerManager.isPaused
    val timerFinishedEvent: SharedFlow<String> = timerManager.timerFinishedEvent

    fun startTimer(title: String, durationMinutes: Int) {
        timerManager.startTimer(title, durationMinutes)
    }

    fun pauseTimer() {
        timerManager.pauseTimer()
    }

    fun resumeExistingTimer() {
        timerManager.resumeExistingTimer()
    }

    fun stopTimer() {
        timerManager.stopTimer()
    }
}
