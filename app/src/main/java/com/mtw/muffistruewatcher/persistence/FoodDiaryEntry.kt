package com.mtw.muffistruewatcher.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "food_diary_entries",
    indices = arrayOf(Index("eaten_date")))
data class FoodDiaryEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val commentary: String,
    val points: Int,
    @ColumnInfo(name = "eaten_date") val eatenDate: LocalDateTime,
    @ColumnInfo(name = "created_date") val createdDate: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "updated_date") val updatedDate: LocalDateTime = LocalDateTime.now()
) : Serializable
