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

package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmArtist
import com.antonioleiva.bandhookkotlin.domain.entity.Artist

class ArtistMapper(private val imageMapper: ImageMapper = ImageMapper()) {

    fun transform(artists: List<LastFmArtist>): List<Artist> {
        return artists.filter { artistHasQualityInfo(it) }.mapNotNull { transform(it) }
    }

    fun transform(artist: LastFmArtist) = artist.mbid?.let {
        Artist(artist.mbid, artist.name, imageMapper.getMainImageUrl(artist.images), artist.bio?.content)
    }

    private fun artistHasQualityInfo(it: LastFmArtist): Boolean {
        return !isArtistMbidEmpty(it) && it.images != null && it.images.isNotEmpty()
    }

    private fun isArtistMbidEmpty(artist: LastFmArtist): Boolean {
        return artist.mbid?.isEmpty() ?: true
    }
}