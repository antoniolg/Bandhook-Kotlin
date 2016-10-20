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

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmService {

    @GET("/2.0/?method=artist.search")
    fun searchArtist(@Query("artist") artist: String): Call<LastFmResponse>

    @GET("/2.0/?method=artist.getinfo")
    fun requestArtistInfo(@Query("mbid") id: String, @Query("lang") language: String): Call<LastFmResponse>

    @GET("/2.0/?method=artist.gettopalbums")
    fun requestAlbums(@Query("mbid") id: String, @Query("artist") artist: String): Call<LastFmResponse>

    @GET("/2.0/?method=artist.getsimilar")
    fun requestSimilar(@Query("mbid") id: String): Call<LastFmResponse>

    @GET("/2.0/?method=album.getInfo")
    fun requestAlbum(@Query("mbid") id: String): Call<LastFmResponse>
}