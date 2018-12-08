package com.github.pullrequest.di.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.pullrequest.ui.main.PRListViewModel
import com.github.pullrequest.data.source.repository.AppDataSource
import com.github.pullrequest.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * View Model Factory Maker for [ViewModel] that require AppDataSource
 */
class AppViewModelFactory @Inject
constructor(private val dataSource: AppDataSource,
            private val schedulerProvider: SchedulerProvider,
            private val compositeDisposable: CompositeDisposable) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PRListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PRListViewModel(dataSource, schedulerProvider, compositeDisposable) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}