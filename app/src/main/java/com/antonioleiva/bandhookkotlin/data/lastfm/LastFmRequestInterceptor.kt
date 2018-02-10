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

package com.antonioleiva.bandhookkotlin.data.lastfm

import okhttp3.Interceptor
import okhttp3.Response


class LastFmRequestInterceptor(private val apiKey: String, private val cacheDuration: Int) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
            .addQueryParameter("api_key", apiKey)
            .addQueryParameter("format", "json")
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .addHeader("Cache-Control", "public, max-age=$cacheDuration")
            .build()

        return chain.proceed(newRequest)
    }
}