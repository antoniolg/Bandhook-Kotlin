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

class ArtistRepositoryImp(val artistDataSets: List<ArtistDataSet>) : ArtistRepository {

    override fun getRecommendedArtists(): List<Artist> {
        for (dataSet in artistDataSets) {
            var result = dataSet.requestRecommendedArtists()
            if (result.isNotEmpty()) {
                return result
            }
        }

        return emptyList()
    }

    override fun getArtist(id: String): Artist {
        // TODO test if result can be null
        for (dataSet in artistDataSets) {
            var result = dataSet.requestArtist(id)
            return result
        }
        return Artist("empty", "empty", "empty")
    }

}