package com.app.habittracker.presentation.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val authRepository: com.app.habittracker.domain.repository.AuthRepository
) : ViewModel() {

    fun checkUserStatus(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(authRepository.isUserLoggedIn())
        }
    }
}
