package com.mtw.muffistruewatcher

import android.content.Context
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntryDao
import com.mtw.muffistruewatcher.persistence.WatcherDatabase
import com.mtw.muffistruewatcher.ui.ViewModelFactory

object HandInjection {

    fun provideFoodDiaryEntryDao(context: Context): FoodDiaryEntryDao {
        return WatcherDatabase.getInstance(context).foodDiaryEntryDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(provideFoodDiaryEntryDao(context))
    }
}