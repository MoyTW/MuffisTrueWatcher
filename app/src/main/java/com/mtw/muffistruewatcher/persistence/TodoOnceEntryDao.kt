package com.mtw.muffistruewatcher.persistence


import androidx.room.*
import io.reactivex.Flowable

@Dao
interface TodoOnceEntryDao {

    @Query("SELECT * FROM todo_once_entries ORDER BY scheduled_date DESC")
    fun fetchAllEntries(): Flowable<List<TodoOnceEntry>>

    @Query("SELECT * FROM todo_once_entries WHERE id = :id")
    fun fetchEntryById(id: String): Flowable<TodoOnceEntry>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertEntry(entry: TodoOnceEntry)

    @Update
    fun updateEntry(entry: TodoOnceEntry)

    @Query("DELETE FROM todo_once_entries WHERE id = :id")
    fun deleteEntry(id: String)
}