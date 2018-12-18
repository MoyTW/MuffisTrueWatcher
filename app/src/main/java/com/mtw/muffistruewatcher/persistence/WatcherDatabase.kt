package com.mtw.muffistruewatcher.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = arrayOf(FoodDiaryEntry::class)
)
abstract class WatcherDatabase : RoomDatabase() {

    abstract fun foodDiaryEntryDao(): FoodDiaryEntryDao

    companion object {
        @Volatile
        private var INSTANCE: WatcherDatabase? = null

        fun getInstance(context: Context): WatcherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, WatcherDatabase::class.java, "WatcherDatabase.db").build()
    }

}