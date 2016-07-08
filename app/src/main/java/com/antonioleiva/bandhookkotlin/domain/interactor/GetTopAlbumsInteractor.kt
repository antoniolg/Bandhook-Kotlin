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

package com.antonioleiva.bandhookkotlin.domain.interactor

import com.antonioleiva.bandhookkotlin.domain.interactor.base.Event
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Interactor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.TopAlbumsEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository

class GetTopAlbumsInteractor(val albumRepository: AlbumRepository) : Interactor {

    var artistId: String? = null
    var artistName: String? = null

    override fun invoke(): Event {
        if (artistId == null && artistName == null) {
            throw IllegalStateException("Either mbid or name should be specified")
        }
        val albums = albumRepository.getTopAlbums(artistId, artistName)
        return TopAlbumsEvent(albums)
    }
}