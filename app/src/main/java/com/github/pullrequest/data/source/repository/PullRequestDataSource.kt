/*
    Copyright 2018 Gaurav Kumar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.github.pullrequest.data.source.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.github.pullrequest.data.models.local.PullRequest
import com.github.pullrequest.data.source.state.LoadingState

import io.reactivex.disposables.CompositeDisposable

/**
 * Pull Request Data Source for Paged Loading
 *
 * Created by gk
 */
class PullRequestDataSource constructor(
        private val owner: String,
        private val repo: String,
        private val state: String,
        private val appDataSource: AppDataSource,
        private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, PullRequest>() {

    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PullRequest>) {
        if (owner.isEmpty() || repo.isEmpty()) return

        loadingState.postValue(LoadingState.FIRST_LOADING)
        val disposable = appDataSource.getPullRequests(owner, repo, state, 1)
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        loadingState.postValue(LoadingState.FIRST_LOADED)
                        callback.onResult(it, null, 2)
                    } else
                        loadingState.postValue(LoadingState.FIRST_EMPTY)
                }, {
                    loadingState.postValue(LoadingState.FIRST_FAILED)
                })
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PullRequest>) {
        if (owner.isEmpty() || repo.isEmpty()) return

        loadingState.postValue(LoadingState.LOADING)
        val disposable = appDataSource.getPullRequests(owner, repo, state, params.key)
                .subscribe({
                    loadingState.postValue(LoadingState.LOADED)

                    //if, calculated page and current page are same,return null to stop fetching data
                    //else, get the next page
                    val isPaginationEnd = it.isNullOrEmpty()
                    val nextKey = (if (isPaginationEnd) null else params.key + 1)

                    callback.onResult(it, nextKey)
                }, {
                    loadingState.postValue(LoadingState.FAILED)
                })
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PullRequest>) {
        //do something
    }

    fun reset() {
        loadingState.postValue(LoadingState.RESET)
        compositeDisposable.clear()
        invalidate()
    }

}
