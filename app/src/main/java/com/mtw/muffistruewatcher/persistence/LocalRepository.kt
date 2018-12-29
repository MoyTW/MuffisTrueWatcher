package com.mtw.muffistruewatcher.persistence

import android.content.Context
import android.os.AsyncTask
import com.mtw.muffistruewatcher.HandInjection
import io.reactivex.Flowable

class LocalRepository internal constructor(context: Context) {

    private val foodDiaryEntryDao: FoodDiaryEntryDao = HandInjection.provideFoodDiaryEntryDao(context)

    fun fetchFoodDiaryEntries(): Flowable<List<FoodDiaryEntry>> {
        return foodDiaryEntryDao.fetchAllEntries()
    }

    private class FoodDiaryEntryAsyncTask internal constructor(private val task: (entry: FoodDiaryEntry) -> Unit):
        AsyncTask<FoodDiaryEntry, Void, Void>() {

        override fun doInBackground(vararg params: FoodDiaryEntry): Void? {
            task(params[0])
            return null
        }
    }

    fun insertFoodDiaryEntry(foodDiaryEntry: FoodDiaryEntry) {
        FoodDiaryEntryAsyncTask { foodDiaryEntryDao.insertEntry(it) }.execute(foodDiaryEntry)
    }

    fun updateFoodDiaryEntry(foodDiaryEntry: FoodDiaryEntry) {
        FoodDiaryEntryAsyncTask { foodDiaryEntryDao.updateEntry(it) }.execute(foodDiaryEntry)
    }

    fun deleteFoodDiaryEntry(foodDiaryEntry: FoodDiaryEntry) {
        FoodDiaryEntryAsyncTask { foodDiaryEntryDao.deleteEntry(it.id) }.execute(foodDiaryEntry)
    }
}
