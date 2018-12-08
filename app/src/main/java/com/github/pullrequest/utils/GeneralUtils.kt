package com.github.pullrequest.utils

object GeneralUtils {

    fun parseSearchResult(query: String): List<String> {
        return query.split("/")
    }
}