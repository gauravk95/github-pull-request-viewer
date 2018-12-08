package com.github.pullrequest.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.pullrequest.data.source.network.NetworkError
import com.github.pullrequest.data.source.repository.AppDataSource
import com.github.pullrequest.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected val dataSource: AppDataSource,
                         protected val schedulerProvider: SchedulerProvider,
                         protected val compositeDisposable: CompositeDisposable) : ViewModel() {

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val errorMsg: MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun isLoading() = isLoading

    fun getErrorMsg() = errorMsg

    fun handleApiError(throwable: Throwable?) {

        if (throwable == null) {
            errorMsg.value = "An Error Occurred"
            return
        }

        val networkError = NetworkError(throwable)
        errorMsg.value = networkError.appErrorMessage

    }

}