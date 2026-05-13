package com.app.habittracker.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.habittracker.presentation.common.FloatingTimer
import com.app.habittracker.presentation.landing.LandingScreen
import com.app.habittracker.presentation.onboarding.OnboardingScreen
import com.app.habittracker.presentation.dashboard.DashboardScreen
import com.app.habittracker.presentation.habits.HabitCreationScreen
import com.app.habittracker.presentation.quests.DailyQuestsScreen
import com.app.habittracker.presentation.bossbattle.BossBattleScreen
import com.app.habittracker.presentation.achievements.AchievementsScreen
import com.app.habittracker.presentation.analytics.AnalyticsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH
        ) {
            composable(Routes.SPLASH) {
                LandingScreen(
                    onNavigateToDashboard = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.LOGIN) {
                com.app.habittracker.presentation.auth.LoginScreen(
                    onNavigateToDashboard = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            
            composable(Routes.ONBOARDING) {
                OnboardingScreen(
                    onNavigateToDashboard = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.DASHBOARD) {
                DashboardScreen(
                    onNavigateToHabitCreation = { navController.navigate(Routes.HABIT_CREATION) },
                    onNavigateToQuests = { navController.navigate(Routes.DAILY_QUESTS) },
                    onNavigateToBossBattle = { navController.navigate(Routes.BOSS_BATTLE) },
                    onNavigateToAchievements = { navController.navigate(Routes.ACHIEVEMENTS) },
                    onNavigateToAnalytics = { navController.navigate(Routes.ANALYTICS) },
                    onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                    onNavigateToCalendar = { navController.navigate(Routes.CALENDAR) }
                )
            }
            
            composable(Routes.HABIT_CREATION) {
                HabitCreationScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(Routes.DAILY_QUESTS) {
                DailyQuestsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(Routes.BOSS_BATTLE) {
                BossBattleScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(Routes.ACHIEVEMENTS) {
                AchievementsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(Routes.ANALYTICS) {
                AnalyticsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToMasteryGallery = { navController.navigate(Routes.MASTERY_GALLERY) }
                )
            }

            composable(Routes.MASTERY_GALLERY) {
                com.app.habittracker.presentation.mastery.MasteryGalleryScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Routes.PROFILE) {
                com.app.habittracker.presentation.profile.ProfileScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onLogout = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.DASHBOARD) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.CALENDAR) {
                com.app.habittracker.presentation.calendar.CalendarScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        FloatingTimer()
    }
}
