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

import com.github.pullrequest.data.models.local.PullRequest
import com.github.pullrequest.data.source.prefs.Preferences
import com.github.pullrequest.di.Local
import com.github.pullrequest.di.Remote
import io.reactivex.Observable

import javax.inject.Inject
import javax.inject.Singleton

/**
 * The central point to communicate to different data sources like DB, SERVER, SHARED PREFS
 *
 * NOTE:
 * [remoteAppDataSource] is used here to get data frm the server
 * [localAppDataSource] and [preference] are only intended for extension purposed in future like local caching
 *
 * Created by gk
 */

@Singleton
class AppDataRepository @Inject
constructor(@param:Remote private val remoteAppDataSource: AppDataSource,
            @param:Local private val localAppDataSource: AppDataSource,
            private val preference: Preferences) : AppDataSource {

    override fun getPullRequests(ownerName: String, repoName: String,
                                 state: String, page: Int,
                                 sortBy: String, direction: String): Observable<List<PullRequest>> {
        return remoteAppDataSource.getPullRequests(ownerName, repoName, state, page, sortBy, direction)
    }
}
