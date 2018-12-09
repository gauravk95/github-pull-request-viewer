package com.github.pullrequest.data.source.state

enum class LoadingStatus {
    RESET,
    FIRST_RUNNING,
    FIRST_SUCCESS,
    FIRST_EMPTY,
    FIRST_FAILED,
    RUNNING,
    SUCCESS,
    FAILED
}