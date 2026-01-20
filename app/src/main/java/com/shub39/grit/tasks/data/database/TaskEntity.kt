package com.shub39.grit.tasks.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val title: String,
    val status: Boolean = false,
    val index: Int = 0,
    @ColumnInfo(name = "reminder", defaultValue = "NULL")
    val reminder: LocalDateTime? = null,
    @ColumnInfo(name = "priority", defaultValue = "0")
    val priority: Int = 0,
    @ColumnInfo(name = "startTime", defaultValue = "NULL")
    val startTime: kotlinx.datetime.LocalTime? = null,
    @ColumnInfo(name = "endTime", defaultValue = "NULL")
    val endTime: kotlinx.datetime.LocalTime? = null,
    @ColumnInfo(name = "isAllDay", defaultValue = "0")
    val isAllDay: Boolean = false,
    @ColumnInfo(name = "isRepeated", defaultValue = "0")
    val isRepeated: Boolean = false,
    @ColumnInfo(name = "repeatWeekdays", defaultValue = "")
    val repeatWeekdays: String = ""
)