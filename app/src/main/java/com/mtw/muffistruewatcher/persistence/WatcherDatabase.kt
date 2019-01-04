package com.mtw.muffistruewatcher.persistence

import android.content.Context
import androidx.room.*
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Converters {
    private val isoDateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    @TypeConverter
    fun localDateTimeFromTimestamp(epochTime: Long?): LocalDateTime? {
        return epochTime?.let { LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneId.systemDefault()) }
    }

    @TypeConverter
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.atZone(ZoneId.systemDefault())?.toEpochSecond()
    }

    @TypeConverter
    fun localDateFromString(stringDate: String?): LocalDate? {
        return stringDate?.let { LocalDate.parse(stringDate, isoDateFormatter) }
    }

    @TypeConverter
    fun localDateToString(localDate: LocalDate?): String? {
        return localDate?.let { localDate.format(isoDateFormatter) }
    }
}

@Database(
    version = 4,
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
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
    }

}