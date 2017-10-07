/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.antonioleiva.bandhookkotlin

import android.app.*
import android.content.*
import com.antonioleiva.bandhookkotlin.di.*
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.*

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(appModule(this@App))
        import(dataModule)
        import(domainModule)
        import(repositoryModule)
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(
                androidActivityScope.lifecycleManager
        )
    }
}

fun Context.asApp() = applicationContext as App

