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
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import com.antonioleiva.bandhookkotlin.repository.dataset.ArtistDataSet

class ArtistRepositoryImpl(private val artistDataSets: List<ArtistDataSet>) : ArtistRepository {

    override fun getRecommendedArtists() = artistDataSets
            .map { it.requestRecommendedArtists() }
            .firstOrNull { it.isNotEmpty() }
            .orEmpty()

    override fun getArtist(id: String): Artist = artistDataSets
            .map { it.requestArtist(id) }
            .firstOrNull { it != null }
            ?: Artist("empty", "empty", "empty")

}