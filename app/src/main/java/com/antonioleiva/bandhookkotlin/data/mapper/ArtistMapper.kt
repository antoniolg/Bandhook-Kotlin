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
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImage
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImageType
import com.antonioleiva.bandhookkotlin.domain.entity.Artist

class ArtistMapper {

    fun transform(artists: List<LastFmArtist>): List<Artist> {
        return artists.filter { artistHasQualityInfo(it) }.map { transform(it) }
    }

    fun transform(artist: LastFmArtist): Artist = Artist(
            artist.mbid,
            artist.name,
            getImage(artist.images),
            artist.bio?.content)

    private fun artistHasQualityInfo(it: LastFmArtist): Boolean {
        return !it.mbid.isEmpty() && it.images.size > 0
    }

    private fun getImage(images: List<LastFmImage>): String {
        val image = images.firstOrNull { it.size == LastFmImageType.MEGA.type }
        return image?.url ?: images.last().url
    }
}