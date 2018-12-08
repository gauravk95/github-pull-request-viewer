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
package com.github.pullrequest.data.source.network

import com.github.pullrequest.data.models.local.PullRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API services to connect to server
 *
 * Created by gk
 */

interface APIService {

    @GET("/repos/{owner}/{repo}/pulls")
    fun getGithubPullRequest(@Path("owner") ownerName: String,
                             @Path("repo") repoName: String,
                             @Query("state") state: String,
                             @Query("page") page: Int,
                             @Query("sort") sortBy: String,
                             @Query("direction") direction: String): Observable<List<PullRequest>>

}
