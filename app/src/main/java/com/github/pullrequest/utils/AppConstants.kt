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
package com.github.pullrequest.utils

/**
 * Constants that will be used through the app
 *
 * NOTE: use [JvmField] if using java with kotlin to avoid [AppConstants.INSTANCE.<name_access>]
 *
 * Created by gk
 */
object AppConstants {
    const val DB_NAME = "app.db"
    const val PREF_NAME = "app_pref"

    const val SORT_ORDER_DESCENDING = "desc"
    const val SORT_ORDER_ASCENDING = "asc"

    const val SORT_BY_CREATED = "created"
    const val SORT_BY_UPDATED = "updated"
    const val SORT_BY_POPULARITY = "popularity"

    const val PR_STATE_OPEN = "open"
    const val PR_STATE_CLOSED = "closed"
    const val PR_STATE_ALL = "all"

}
