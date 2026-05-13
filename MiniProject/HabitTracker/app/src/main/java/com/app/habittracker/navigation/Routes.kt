package com.app.habittracker.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val DASHBOARD = "dashboard"
    const val HABIT_CREATION = "habit_creation"
    const val HABIT_DETAILS = "habit_details/{habitId}"
    const val DAILY_QUESTS = "daily_quests"
    const val BOSS_BATTLE = "boss_battle"
    const val ACHIEVEMENTS = "achievements"
    const val ANALYTICS = "analytics"
    const val PROFILE = "profile"
    const val CALENDAR = "calendar"
    const val MASTERY_GALLERY = "mastery_gallery"

    fun habitDetails(habitId: Int) = "habit_details/$habitId"
}
