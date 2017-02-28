/*
 * Copyright (C) 2016 Alexey Verein
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

import com.antonioleiva.bandhookkotlin.*
import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.BizException.*
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.repository.dataset.AlbumDataSet
import org.funktionale.collections.tail

class AlbumRepositoryImpl(val albumDataSets: List<AlbumDataSet>) : AlbumRepository {

    /** Trampolined (async) recursion until all DataSets are exhausted or a right result is found */
    fun <E, A> recurseDataSets(currentDataSets: List<AlbumDataSet>, acc: Result<E, A>, f : (AlbumDataSet) -> Result<E, A>): Result<E, A> =
        if (currentDataSets.isEmpty()) { acc }
        else {
            val current = currentDataSets.get(0)
            f(current).recoverWith {
                recurseDataSets(currentDataSets.tail(), f(current), f) }
            }


    override fun getAlbum(id: String): Result<AlbumNotFound, Album> =
        recurseDataSets(
                currentDataSets = albumDataSets,
                acc = ResultT.raiseError<AlbumNotFound, Album>(AlbumNotFound(id)),
                f = { it.requestAlbum(id) }
        )


    override fun getTopAlbums(artistId: String?, artistName: String?): List<Album> {
        for (dataSet in albumDataSets) {
            val result = dataSet.requestTopAlbums(artistId, artistName)
            if (result.isNotEmpty()) {
                return result
            }
        }

        return emptyList()
    }

}