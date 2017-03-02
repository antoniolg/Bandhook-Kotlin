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

import com.antonioleiva.bandhookkotlin.Result
import com.antonioleiva.bandhookkotlin.ResultT
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.ArtistNotFound
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import com.antonioleiva.bandhookkotlin.recoverWith
import com.antonioleiva.bandhookkotlin.repository.dataset.ArtistDataSet
import org.funktionale.collections.tail

class ArtistRepositoryImp(val artistDataSets: List<ArtistDataSet>) : ArtistRepository {

    /** Trampolined (async) recursion until all DataSets are exhausted or a right result is found */
    fun <E, A> recurseDataSets(currentDataSets: List<ArtistDataSet>, acc: Result<E, A>, f:
    (ArtistDataSet) -> Result<E, A>): Result<E, A> =
            if (currentDataSets.isEmpty()) {
                acc
            } else {
                val current = currentDataSets.get(0)
                f(current).recoverWith {
                    recurseDataSets(currentDataSets.tail(), f(current), f)
                }
            }

    override fun getArtist(id: String): Result<ArtistNotFound, Artist> =
            recurseDataSets(
                    currentDataSets = artistDataSets,
                    acc = ResultT.raiseError<ArtistNotFound, Artist>(ArtistNotFound(id)),
                    f = { it.requestArtist(id) }
            )

    override fun getRecommendedArtists(): List<Artist> {
        for (dataSet in artistDataSets) {
            val result = dataSet.requestRecommendedArtists()
            if (result.isNotEmpty()) {
                return result
            }
        }

        return emptyList()
    }

}
