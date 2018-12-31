package com.mtw.muffistruewatcher.persistence

import androidx.room.*
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity(
    tableName = "todo_once_entries",
    indices = [Index("scheduled_date"), Index("due_by_date"), Index("completed_date")]
)
data class TodoOnceEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val commentary: String,
    val points: Int,
    val priority: Int,

    @ColumnInfo(name = "missable") val missable: Boolean,
    @ColumnInfo(name = "scheduled_date") val scheduledDate: LocalDate?, // Null = "hasn't been scheduled"
    @ColumnInfo(name = "due_by_date") val dueByDate: LocalDate?, // Null = "hasn't been decided"
    @ColumnInfo(name = "completed_date") val completedDate: LocalDateTime?, // Null = "hasn't been completed"

    @ColumnInfo(name = "created_date") val createdDate: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "updated_date") val updatedDate: LocalDateTime = LocalDateTime.now()
) : Serializable
