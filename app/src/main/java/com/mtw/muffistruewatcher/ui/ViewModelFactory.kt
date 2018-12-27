package com.mtw.muffistruewatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtw.muffistruewatcher.persistence.LocalRepository
import com.mtw.muffistruewatcher.ui.fooddiary.FoodDiaryEntryViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val localRepository: LocalRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodDiaryEntryViewModel::class.java)) {
            return FoodDiaryEntryViewModel(localRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}