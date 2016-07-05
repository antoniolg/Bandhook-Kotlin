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

package com.antonioleiva.bandhookkotlin.di

import android.util.Log
import com.antonioleiva.bandhookkotlin.BuildConfig
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmRequestInterceptor
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.squareup.okhttp.Cache
import com.squareup.okhttp.OkHttpClient
import retrofit.RestAdapter
import retrofit.client.OkClient
import java.io.IOException

interface DataModule : LastFmServiceSingleton

interface LastFmServiceSingleton {
    val lastFmService: LastFmService
}

class DataModuleImpl(appModule: AppModule) : DataModule, AppContext by appModule {

    override val lastFmService: LastFmService

    init {
        var responseCache: Cache? = null
        try {
            responseCache = Cache(appContext.cacheDir, 10 * 1024 * 1024.toLong())
        } catch (e: IOException) {
            Log.e("Retrofit", "Could not create http cache", e)
        }

        val okHttpClient = OkHttpClient()
        okHttpClient.setCache(responseCache)

        val apiKey = appContext.getString(R.string.last_fm_api_key)
        val cacheDuration = appContext.resources.getInteger(R.integer.cache_duration)

        val restAdapter = RestAdapter.Builder()
                .setEndpoint("http://ws.audioscrobbler.com")
                .setRequestInterceptor(LastFmRequestInterceptor(apiKey, cacheDuration))
                .setLogLevel(if (BuildConfig.DEBUG) RestAdapter.LogLevel.FULL else RestAdapter.LogLevel.NONE)
                .setClient(OkClient(okHttpClient))
                .build();

        lastFmService = restAdapter.create(LastFmService::class.java) ;
    }
}