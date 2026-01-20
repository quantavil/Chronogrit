package com.shub39.grit.core.tasks.domain

import kotlinx.datetime.LocalDateTime
import com.shub39.grit.core.utils.toFormattedString
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Long = 0,
    val categoryId: Long,
    val title: String,
    val index: Int = 0,
    val status: Boolean = false,
    val reminder: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.NONE,
    val startTime: kotlinx.datetime.LocalTime? = null,
    val endTime: kotlinx.datetime.LocalTime? = null,
    val isAllDay: Boolean = false,
    val isRepeated: Boolean = false,
    val repeatWeekdays: String = ""
) {
    fun isAllDayTask(): Boolean {
        return (startTime == null && endTime == null) || (startTime == endTime)
    }

    fun getFormattedTime(is24Hr: Boolean): String {
        if (startTime == null || endTime == null) return ""
        val start = startTime.toFormattedString(is24Hr)
        val end = endTime.toFormattedString(is24Hr)
        return "$start - $end"
    }

    fun getDurationMinutes(): Long {
        if (startTime == null || endTime == null) return 0
        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute
        return (endMinutes - startMinutes).coerceAtLeast(0).toLong()
    }

    fun getFormattedDuration(): String {
        val duration = getDurationMinutes()
        if (duration <= 0) return ""
        val hours = duration / 60
        val minutes = duration % 60
        
        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
            hours > 0 -> "${hours}h"
            else -> "${minutes}m"
        }
    }

    fun getRepeatWeekList(): List<Int> {
        return if (repeatWeekdays.isEmpty())
            emptyList()
        else
            repeatWeekdays.split(",")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
    }

    fun getWeekDaysTitle(): String {
        val weekDays = getRepeatWeekList()
        val weekdaysTitle = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        
        val selectedDays = weekDays.mapNotNull { 
            if (it in weekdaysTitle.indices) weekdaysTitle[it] else null 
        }
        return selectedDays.joinToString(", ")
    }
}