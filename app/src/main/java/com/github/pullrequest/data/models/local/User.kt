package com.github.pullrequest.data.models.local

import java.io.Serializable

data class User(
        val id: String,
        val login: String,
        val url: String) : Serializable