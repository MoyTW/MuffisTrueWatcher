package com.mtw.muffistruewatcher.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface FoodDiaryEntryDao {

    @Query("SELECT * FROM food_diary_entries ORDER BY id ASC")
    fun fetchAllEntries(): Flowable<List<FoodDiaryEntry>>

    @Query("SELECT * FROM food_diary_entries WHERE id = :id")
    fun fetchEntryById(id: String): Flowable<FoodDiaryEntry>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertEntry(entry: FoodDiaryEntry)

    @Query("DELETE FROM food_diary_entries WHERE id = :id")
    fun deleteEntry(id: String)
}