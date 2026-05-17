package com.app.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import com.app.habittracker.ui.theme.HabitPulseTheme
import com.app.habittracker.data.local.PreferencesManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request notification permission for Android 13+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            androidx.core.app.ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        com.app.habittracker.workers.WorkScheduler.scheduleDailyReset(this)
        com.app.habittracker.workers.WorkScheduler.scheduleHabitStatusCheck(this)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by preferencesManager.isDarkMode.collectAsState(initial = true)
            HabitPulseTheme(darkTheme = isDarkTheme) {
                com.app.habittracker.navigation.NavGraph()
            }
        }
    }
}
