package com.mtw.muffistruewatcher

import android.content.Context
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntryDao
import com.mtw.muffistruewatcher.persistence.LocalRepository
import com.mtw.muffistruewatcher.persistence.WatcherDatabase
import com.mtw.muffistruewatcher.ui.ViewModelFactory

// I need to figure out whether or not I want to use DI, because this isn't...great.
object HandInjection {

    fun provideFoodDiaryEntryDao(context: Context): FoodDiaryEntryDao {
        return WatcherDatabase.getInstance(context).foodDiaryEntryDao()
    }

    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepository(context)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(provideLocalRepository(context))
    }
}