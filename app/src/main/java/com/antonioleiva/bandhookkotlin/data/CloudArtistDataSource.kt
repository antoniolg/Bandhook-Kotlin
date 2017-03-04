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

import com.antonioleiva.bandhookkotlin.NonEmptyList
import com.antonioleiva.bandhookkotlin.Result
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.antonioleiva.bandhookkotlin.data.mapper.ArtistMapper
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.ArtistNotFound
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.RecomendationsNotFound
import com.antonioleiva.bandhookkotlin.left
import com.antonioleiva.bandhookkotlin.repository.datasource.ArtistDataSource
import com.antonioleiva.bandhookkotlin.right
import org.funktionale.collections.tail

class CloudArtistDataSource(val language: String, val lastFmService: LastFmService) : ArtistDataSource {

    val coldplayMbid = "cc197bad-dc9c-440d-a5b5-d52ba2e14234"

    override fun get(id: String): Result<ArtistNotFound, Artist> =
            lastFmService.requestArtistInfo(id, language).asResult {
                ArtistMapper().transform(artist).fold(
                        { ArtistNotFound(id).left() },
                        { it.right() }
                )
            }

    override fun requestRecommendedArtists(): Result<RecomendationsNotFound, NonEmptyList<Artist>> =
            lastFmService.requestSimilar(coldplayMbid).asResult {
                val results = ArtistMapper().transform(similarArtists.artists)
                if (results.isEmpty()) RecomendationsNotFound.left()
                else NonEmptyList(results[0], results.tail()).right()
            }

}
