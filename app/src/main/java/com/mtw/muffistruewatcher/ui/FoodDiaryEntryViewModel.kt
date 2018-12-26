package com.mtw.muffistruewatcher.ui

import androidx.lifecycle.ViewModel
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import com.mtw.muffistruewatcher.persistence.LocalRepository
import io.reactivex.Flowable
import java.time.LocalDateTime

class FoodDiaryEntryViewModel(private val localRepository: LocalRepository): ViewModel() {

    fun foodDiaryEntries(): Flowable<List<FoodDiaryEntry>> {
        return localRepository.fetchFoodDiaryEntries()
    }

    fun insert(name: String, description: String) {
        localRepository.insertFoodDiaryEntry(
            FoodDiaryEntry(
                name = name,
                commentary = description,
                eatenDate = LocalDateTime.now()
            )
        )
    }
}