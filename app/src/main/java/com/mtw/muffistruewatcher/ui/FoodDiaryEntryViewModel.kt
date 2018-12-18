package com.mtw.muffistruewatcher.ui

import androidx.lifecycle.ViewModel
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntryDao
import io.reactivex.Completable
import io.reactivex.Flowable

class FoodDiaryEntryViewModel(private val foodDiaryEntryDao: FoodDiaryEntryDao): ViewModel() {

    fun foodDiaryEntries(): Flowable<List<FoodDiaryEntry>> {
        return foodDiaryEntryDao.fetchAllEntries()
    }

    fun insert(description: String): Completable {
        return Completable.fromAction {
            foodDiaryEntryDao.insertEntry(FoodDiaryEntry(description = description))
        }
    }
}