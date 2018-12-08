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
package com.github.pullrequest.di.component

import android.app.Application
import android.content.Context

import com.github.pullrequest.base.MainApplication
import com.github.pullrequest.data.source.repository.AppDataRepository
import com.github.pullrequest.data.source.repository.AppDataSource
import com.github.pullrequest.di.ApplicationContext
import com.github.pullrequest.di.module.ApplicationModule
import com.github.pullrequest.di.module.DataModule
import com.github.pullrequest.di.module.NetworkModule

import javax.inject.Singleton

import dagger.Component

/**
 * Application component connecting modules that have application scope
 *
 * Created by gk
 */

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun getAppRepository(): AppDataSource

    fun inject(app: MainApplication)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

}
