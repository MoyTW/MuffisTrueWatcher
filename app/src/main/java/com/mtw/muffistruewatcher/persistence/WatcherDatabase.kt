package com.mtw.muffistruewatcher.persistence

import android.content.Context
import androidx.room.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun localDateTimeFromTimestamp(epochTime: Long?): LocalDateTime? {
        return epochTime?.let { LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneId.systemDefault()) }
    }

    @TypeConverter
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.atZone(ZoneId.systemDefault())?.toEpochSecond()
    }
}

@Database(
    version = 2,
    entities = arrayOf(FoodDiaryEntry::class)
)
@TypeConverters(Converters::class)
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
            Room.databaseBuilder(context.applicationContext, WatcherDatabase::class.java, "WatcherDatabase.db")
                //.fallbackToDestructiveMigration() // Uncomment only when intentionally wiping DB!
                .build()
    }

}