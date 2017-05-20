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

package com.antonioleiva.bandhookkotlin.repository

import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.ArtistNotFound
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.RecomendationsNotFound
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import com.antonioleiva.bandhookkotlin.repository.datasource.ArtistDataSource
import com.github.finecinnamon.NonEmptyList
import com.github.finecinnamon.Result
import com.github.finecinnamon.Result.Factory.firstSuccessIn
import com.github.finecinnamon.Result.Factory.raiseError
import com.github.finecinnamon.binding

class ArtistRepositoryImpl(val dataSources: List<ArtistDataSource>) : ArtistRepository {

    override fun getArtist(id: String): Result<ArtistNotFound, Artist> =
            binding {
                val r = bind(firstSuccessIn(
                        fa = dataSources,
                        acc = raiseError(ArtistNotFound(id)),
                        f = { it.get(id) }
                ))
                yields(r)
            }

    override fun getRecommendedArtists(): Result<RecomendationsNotFound, NonEmptyList<Artist>> =
            binding {
                val r = bind(firstSuccessIn(
                        fa = dataSources,
                        acc = raiseError(RecomendationsNotFound),
                        f = { it.requestRecommendedArtists() }
                ))
                yields(r)
            }

}
