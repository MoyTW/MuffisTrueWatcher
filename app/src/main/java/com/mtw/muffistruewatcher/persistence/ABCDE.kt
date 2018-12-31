package com.mtw.muffistruewatcher.persistence

import androidx.room.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Entity(
    tableName = "todo_once_entries_to_todo_recurring_entry",
    primaryKeys = ["todo_once_entry_id", "todo_recurring_entry_id"],
    foreignKeys = [ForeignKey(
        entity = TodoOnceEntry::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("todo_once_entry_id"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = TodoRecurringEntry::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("todo_recurring_entry_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TodoOnceEntryToTodoRecurringEntry(
    @ColumnInfo(name = "todo_once_entry_id") val todoOnceEntryId: String,
    @ColumnInfo(name = "todo_recurring_entry_id") val todoRecurringEntryId: String
)

@Entity(
    tableName = "todo_recurring_entries",
    indices = [Index("scheduled_date"), Index("due_by_date"), Index("completed_date")]
)
data class TodoRecurringEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val commentary: String,

    val childBaseName: String,
    val childBaseCommentary: String,
    val childBasePoints: Int,
    val childBasePriority: Int,

    @ColumnInfo(name = "missable") val missable: Boolean,
    @ColumnInfo(name = "scheduled_date") val scheduledDate: LocalDate?, // Null = "hasn't been scheduled"
    @ColumnInfo(name = "due_by_date") val dueByDate: LocalDate?, // Null = "never due" aka "ceaseless labor"
    @ColumnInfo(name = "completed_date") val completedDate: LocalDateTime?, // Null = "hasn't been completed"

    @ColumnInfo(name = "created_date") val createdDate: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "updated_date") val updatedDate: LocalDateTime = LocalDateTime.now()
)