package com.app.habittracker.domain.engines

import com.app.habittracker.data.local.PreferencesManager
import com.app.habittracker.di.ApplicationScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerManager @Inject constructor(
    private val preferencesManager: PreferencesManager,
    @ApplicationScope private val scope: CoroutineScope
) {
    private val _activeTimerTitle = MutableStateFlow<String?>(null)
    val activeTimerTitle: StateFlow<String?> = _activeTimerTitle.asStateFlow()

    private val _timerRemainingSeconds = MutableStateFlow(0)
    val timerRemainingSeconds: StateFlow<Int> = _timerRemainingSeconds.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _timerFinishedEvent = MutableSharedFlow<String>()
    val timerFinishedEvent: SharedFlow<String> = _timerFinishedEvent.asSharedFlow()

    private var timerJob: Job? = null

    init {
        scope.launch {
            combine(
                preferencesManager.activeTimerTitle,
                preferencesManager.isActiveTimerPaused,
                preferencesManager.activeTimerRemainingSeconds
            ) { title, paused, remaining -> Triple(title, paused, remaining) }
            .collect { (title, paused, remaining) ->
                // Update state flows only if they changed
                if (_activeTimerTitle.value != title) _activeTimerTitle.value = title
                if (_isPaused.value != paused) _isPaused.value = paused
                
                // Only update remaining seconds if the timer job is NOT running locally
                // to avoid the 5s restart loop triggered by saveActiveTimer
                if (timerJob == null || !timerJob!!.isActive) {
                    _timerRemainingSeconds.value = remaining
                }
                
                if (title != null && timerJob == null && !paused && remaining > 0) {
                    resumeTimer()
                }
            }
        }
    }

    private fun resumeTimer() {
        timerJob?.cancel()
        _isPaused.value = false
        timerJob = scope.launch {
            while (_timerRemainingSeconds.value > 0) {
                delay(1000)
                if (!_isPaused.value) {
                    _timerRemainingSeconds.value -= 1
                    if (_timerRemainingSeconds.value % 5 == 0) {
                        preferencesManager.saveActiveTimer(_activeTimerTitle.value, _timerRemainingSeconds.value, _isPaused.value)
                    }
                }
            }
            if (_timerRemainingSeconds.value <= 0) {
                _activeTimerTitle.value?.let { _timerFinishedEvent.emit(it) }
                stopTimer()
            }
        }
    }

    fun startTimer(title: String, durationMinutes: Int) {
        timerJob?.cancel()
        _isPaused.value = false
        _activeTimerTitle.value = title
        _timerRemainingSeconds.value = durationMinutes * 60
        scope.launch {
            preferencesManager.saveActiveTimer(title, _timerRemainingSeconds.value, false)
        }
        resumeTimer()
    }

    fun pauseTimer() {
        _isPaused.value = true
        scope.launch {
            preferencesManager.saveActiveTimer(_activeTimerTitle.value, _timerRemainingSeconds.value, true)
        }
    }

    fun resumeExistingTimer() {
        if (_isPaused.value) {
            resumeTimer()
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _isPaused.value = false
        scope.launch {
            preferencesManager.saveActiveTimer(null, 0, false)
        }
        _activeTimerTitle.value = null
        _timerRemainingSeconds.value = 0
    }
}
