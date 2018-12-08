package com.github.pullrequest.ui.main.prlist

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.github.pullrequest.R
import com.github.pullrequest.base.BaseViewModel
import com.github.pullrequest.data.models.local.PullRequest
import com.github.pullrequest.data.source.repository.AppDataSource
import com.github.pullrequest.utils.AppConstants
import com.github.pullrequest.utils.GeneralUtils
import com.github.pullrequest.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
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

    val searchStatus = ObservableField<Int>(R.string.search_msg_start_searching)
    val itemList: MutableLiveData<List<PullRequest>> = MutableLiveData()
    val toastMsg: MutableLiveData<Int> = MutableLiveData()

    /**
     * Search for open pull request based on the [query]
     */
    fun search(query: String?) {

        if (query.isNullOrEmpty()) {
            toastMsg.value = R.string.search_query_empty
            return
        }

        //parse the search query to get the owner and repo
        val parseResult = GeneralUtils.parseSearchResult(query)
        if (parseResult.size != 2 || (parseResult[0].isEmpty() || parseResult[1].isEmpty())) {
            toastMsg.value = R.string.search_query_invalid
            return
        }

        isLoading().value = true
        val disposable = dataSource.getPullRequests(parseResult[0], parseResult[1], AppConstants.PR_STATE_OPEN)
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe({ items: List<PullRequest> ->
                    isLoading().value = false
                    if (items.isNotEmpty())
                        itemList.value = items
                    else
                        searchStatus.set(R.string.search_msg_repo_not_found)
                }, { throwable: Throwable? ->
                    isLoading().value = false
                    itemList.value = null
                    searchStatus.set(R.string.search_msg_repo_not_found)
                    handleApiError(throwable)
                })
        compositeDisposable.add(disposable)
    }

}