package com.app.habittracker.util

import java.text.SimpleDateFormat
import java.util.Locale

object TimeUtils {
    private val sdf24 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    private val sdf12 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

    fun formatTo12h(time24: String): String {
        return try {
            val date = sdf24.parse(time24)
            if (date != null) sdf12.format(date) else time24
        } catch (e: Exception) {
            time24
        }
    }

    fun formatTo24h(time12: String): String {
        return try {
            val date = sdf12.parse(time12)
            if (date != null) sdf24.format(date) else time12
        } catch (e: Exception) {
            time12
        }
    }
    fun isTimeInRange(current: String, start: String, end: String): Boolean {
        val current24 = formatTo24h(current)
        val start24 = formatTo24h(start)
        val end24 = formatTo24h(end)
        
        return if (start24 <= end24) {
            current24 in start24..end24
        } else {
            // Crosses midnight
            current24 >= start24 || current24 <= end24
        }
    }

    fun isAfter(current: String, target: String): Boolean {
        val current24 = formatTo24h(current)
        val target24 = formatTo24h(target)
        return current24 > target24
    }

    fun getUtcDateString(timestamp: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        sdf.timeZone = java.util.TimeZone.getTimeZone("UTC")
        return sdf.format(java.util.Date(timestamp))
    }

    fun getUtcTimestamp(): Long {
        return System.currentTimeMillis()
    }

    fun getCurrentTime24h(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        return sdf.format(java.util.Date())
    }
}
