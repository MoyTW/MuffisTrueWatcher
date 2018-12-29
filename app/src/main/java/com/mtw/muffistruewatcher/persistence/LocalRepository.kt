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

    fun insertFoodDiaryEntry(foodDiaryEntry: FoodDiaryEntry) {
        InsertAsyncTask(foodDiaryEntryDao).execute(foodDiaryEntry)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: FoodDiaryEntryDao) :
        AsyncTask<FoodDiaryEntry, Void, Void>() {

        override fun doInBackground(vararg params: FoodDiaryEntry): Void? {
            mAsyncTaskDao.insertEntry(params[0])
            return null
        }
    }

    fun updateFoodDiaryEntry(foodDiaryEntry: FoodDiaryEntry) {
        UpdateAsyncTask(foodDiaryEntryDao).execute(foodDiaryEntry)
    }

    // Awkward, but for now I'll keep the pattern. If I have a third one I'll consider simplification.
    private class UpdateAsyncTask internal constructor(private val mAsyncTaskDao: FoodDiaryEntryDao) :
        AsyncTask<FoodDiaryEntry, Void, Void>() {

        override fun doInBackground(vararg params: FoodDiaryEntry): Void? {
            mAsyncTaskDao.updateEntry(params[0])
            return null
        }
    }
}
