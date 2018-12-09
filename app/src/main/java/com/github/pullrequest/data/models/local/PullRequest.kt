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
package com.github.pullrequest.data.models.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.text.format.DateUtils
import java.io.Serializable
import java.util.Date

/**
 * Describes the data to be modeled
 * Created by gk
 */

@Entity(tableName = "item")
data class PullRequest(
        @field:PrimaryKey
        val id: String,
        val url: String,
        val html_url: String,
        val number: Int,
        val state: String,
        val locked: Boolean = false,
        val title: String,
        val body: String,
        val user: User,
        val labels: List<Label>,
        val created_at: Date,
        val update_at: Date) : Serializable {

    fun getAgoTime(): String {
        return DateUtils.getRelativeTimeSpanString(created_at.time).toString()
    }
}