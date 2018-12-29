package com.mtw.muffistruewatcher.persistence

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface FoodDiaryEntryDao {

    @Query("SELECT * FROM food_diary_entries ORDER BY eaten_date DESC")
    fun fetchAllEntries(): Flowable<List<FoodDiaryEntry>>

    @Query("SELECT * FROM food_diary_entries WHERE id = :id")
    fun fetchEntryById(id: String): Flowable<FoodDiaryEntry>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertEntry(entry: FoodDiaryEntry)

    @Update
    fun updateEntry(entry: FoodDiaryEntry)

    @Query("DELETE FROM food_diary_entries WHERE id = :id")
    fun deleteEntry(id: String)
}