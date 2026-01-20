package com.shub39.grit.core.tasks.domain

enum class TaskPriority(val color: Long) {
    LOW(0xFF4CAF50), // Green
    MEDIUM(0xFFFFC107), // Amber
    HIGH(0xFFF44336), // Red
    NONE(0x00000000); // Transparent/None

    companion object {
        fun fromOrdinal(ordinal: Int): TaskPriority = entries.getOrElse(ordinal) { NONE }
    }
}
