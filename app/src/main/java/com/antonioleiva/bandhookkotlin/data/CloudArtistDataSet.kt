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

package com.antonioleiva.bandhookkotlin.data

import com.antonioleiva.bandhookkotlin.Result
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmResponse
import com.antonioleiva.bandhookkotlin.data.mapper.ArtistMapper
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.ArtistNotFound
import com.antonioleiva.bandhookkotlin.left
import com.antonioleiva.bandhookkotlin.repository.dataset.ArtistDataSet
import com.antonioleiva.bandhookkotlin.right

class CloudArtistDataSet(val language: String, val lastFmService: LastFmService) : ArtistDataSet {

    val coldplayMbid = "cc197bad-dc9c-440d-a5b5-d52ba2e14234"

    override fun requestArtist(mbid: String): Result<ArtistNotFound, Artist> {
        return lastFmService.requestArtistInfo(mbid, language).asResult<ArtistNotFound,
                LastFmResponse, Artist> {
            ArtistMapper().transform(artist).fold(
                    { ArtistNotFound(mbid).left<ArtistNotFound, Artist>() },
                    { it.right<ArtistNotFound, Artist>() }
            )
        }
    }

    override fun requestRecommendedArtists(): List<Artist> =
            lastFmService.requestSimilar(coldplayMbid).unwrapCall {
                // Search for coldplay similar artists.
                ArtistMapper().transform(similarArtists.artists)
            }

}
