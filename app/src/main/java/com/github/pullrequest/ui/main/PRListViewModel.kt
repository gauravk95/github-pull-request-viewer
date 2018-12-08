package com.github.pullrequest.ui.main

import android.arch.lifecycle.MutableLiveData
import com.github.pullrequest.base.BaseViewModel
import com.github.pullrequest.data.models.local.PullRequest
import com.github.pullrequest.data.source.repository.AppDataSource
import com.github.pullrequest.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ViewModel for the [MainActivity] screen.
 * The ViewModel works with the [AppDataSource] to get the data.
 */
class PRListViewModel @Inject
constructor(appRepository: AppDataSource,
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable) :
        BaseViewModel(appRepository, schedulerProvider, compositeDisposable) {

    private val itemList: MutableLiveData<List<PullRequest>> = MutableLiveData()

    private lateinit var disposable: Disposable

    init {
        loadItems()
    }

    fun getItemList() = itemList

    private fun loadItems() {
        isLoading().value = true

        disposable = dataSource.getPullRequests("square", "okhttp", "open")
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe({ items: List<PullRequest> ->
                    isLoading().value = false
                    itemList.value = items
                }, { throwable: Throwable? ->
                    isLoading().value = false
                    handleApiError(throwable)
                })

        compositeDisposable.add(disposable)
    }

}