package com.app.habittracker.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    val isOnboardingCompleted: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
        }

    val isDarkMode: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] ?: true // Default to dark mode
        }

    suspend fun saveOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun saveIsDarkMode(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] = isDark
        }
    }

    val currentUserId: Flow<String?> = dataStore.data
        .map { it[PreferencesKeys.CURRENT_USER_ID] }

    suspend fun saveCurrentUserId(userId: String?) {
        dataStore.edit { preferences ->
            if (userId == null) {
                preferences.remove(PreferencesKeys.CURRENT_USER_ID)
            } else {
                preferences[PreferencesKeys.CURRENT_USER_ID] = userId
            }
        }
    }

    val activeTimerTitle: Flow<String?> = dataStore.data
        .map { it[PreferencesKeys.ACTIVE_TIMER_TITLE] }

    val activeTimerRemainingSeconds: Flow<Int> = dataStore.data
        .map { it[PreferencesKeys.ACTIVE_TIMER_REMAINING] ?: 0 }

    val isActiveTimerPaused: Flow<Boolean> = dataStore.data
        .map { it[PreferencesKeys.ACTIVE_TIMER_PAUSED] ?: false }

    suspend fun saveActiveTimer(title: String?, remainingSeconds: Int, isPaused: Boolean = false) {
        dataStore.edit { preferences ->
            if (title == null) {
                preferences.remove(PreferencesKeys.ACTIVE_TIMER_TITLE)
                preferences.remove(PreferencesKeys.ACTIVE_TIMER_REMAINING)
                preferences.remove(PreferencesKeys.ACTIVE_TIMER_PAUSED)
            } else {
                preferences[PreferencesKeys.ACTIVE_TIMER_TITLE] = title
                preferences[PreferencesKeys.ACTIVE_TIMER_REMAINING] = remainingSeconds
                preferences[PreferencesKeys.ACTIVE_TIMER_PAUSED] = isPaused
            }
        }
    }

    private object PreferencesKeys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val ACTIVE_TIMER_TITLE = androidx.datastore.preferences.core.stringPreferencesKey("active_timer_title")
        val ACTIVE_TIMER_REMAINING = androidx.datastore.preferences.core.intPreferencesKey("active_timer_remaining")
        val ACTIVE_TIMER_PAUSED = booleanPreferencesKey("active_timer_paused")
        val CURRENT_USER_ID = androidx.datastore.preferences.core.stringPreferencesKey("current_user_id")
    }
}
