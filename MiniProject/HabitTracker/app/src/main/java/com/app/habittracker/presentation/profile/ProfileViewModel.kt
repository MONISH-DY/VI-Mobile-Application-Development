package com.app.habittracker.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.entities.UserEntity
import com.app.habittracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: com.app.habittracker.domain.repository.AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getUser().collect {
                _user.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _isLoggedOut.value = true
        }
    }

    fun updateName(name: String) {
        _user.value = _user.value?.copy(name = name)
    }

    fun updateDifficulty(difficulty: String) {
        _user.value = _user.value?.copy(difficultyPreference = difficulty)
    }

    fun updateReminderTime(time: String) {
        _user.value = _user.value?.copy(reminderTime = time)
    }

    fun saveChanges() {
        viewModelScope.launch {
            _user.value?.let {
                userRepository.updateProfileSettings(
                    name = it.name,
                    difficulty = it.difficultyPreference,
                    reminderTime = it.reminderTime
                )
                _saveSuccess.value = true
                kotlinx.coroutines.delay(2000)
                _saveSuccess.value = false
            }
        }
    }
}
