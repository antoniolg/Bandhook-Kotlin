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

package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.github.finecinnamon.NonEmptyList

class ImageTitleDataMapper() {

    fun transformArtists(artists: NonEmptyList<Artist>): NonEmptyList<ImageTitle> {
        return artists.map { ImageTitle(it.id, it.name, it.url) }
    }

    fun transformAlbums(albums: List<Album>): List<ImageTitle> {
        return albums.map { ImageTitle(it.id, it.name, it.url) }
    }
}