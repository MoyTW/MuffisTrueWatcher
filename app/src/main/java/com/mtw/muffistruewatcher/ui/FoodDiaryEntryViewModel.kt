package com.mtw.muffistruewatcher.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntryDao
import com.mtw.muffistruewatcher.persistence.LocalRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class FoodDiaryEntryViewModel(private val localRepository: LocalRepository): ViewModel() {

    fun foodDiaryEntries(): Flowable<List<FoodDiaryEntry>> {
        return localRepository.fetchFoodDiaryEntries()
    }

    fun insert(description: String) {
        localRepository.insertFoodDiaryEntry(FoodDiaryEntry(description = description))
    }
}