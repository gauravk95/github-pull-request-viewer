package com.github.pullrequest.data.source.factory

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.github.pullrequest.data.models.local.PullRequest
import com.github.pullrequest.data.source.repository.AppDataSource
import com.github.pullrequest.data.source.repository.PullRequestDataSource
import io.reactivex.disposables.CompositeDisposable

class PullRequestDataFactory(private val appDataSource: AppDataSource,
                             private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, PullRequest>() {

    val mutableLiveData: MutableLiveData<PullRequestDataSource> = MutableLiveData()

    private var owner: String = ""
    private var repo: String = ""
    private var state: String = ""

    override fun create(): DataSource<Int, PullRequest> {
        val dataSource = PullRequestDataSource(owner, repo, state, appDataSource, compositeDisposable)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }

    fun setSearchQuery(owner: String, repo: String, state: String) {
        this.owner = owner
        this.repo = repo
        this.state = state
        mutableLiveData.value?.reset()
    }
}