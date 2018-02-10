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

package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmAlbum
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmAlbumDetail
import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist

class AlbumMapper(
    private val artistMapper: ArtistMapper = ArtistMapper(),
    private val imageMapper: ImageMapper = ImageMapper(),
    private val trackMapper: TrackMapper = TrackMapper()
) {

    fun transform(albums: List<LastFmAlbum>): List<Album> {
        return albums.filter { albumHasQualityInfo(it) }.mapNotNull { transform(it) }
    }

    private fun albumHasQualityInfo(album: LastFmAlbum): Boolean {
        return !isAlbumMbidEmpty(album) && album.images.isNotEmpty()
    }

    private fun isAlbumMbidEmpty(album: LastFmAlbum): Boolean {
        return album.mbid?.isEmpty() ?: true
    }

    fun transform(album: LastFmAlbumDetail) = album.mbid?.let {
        Album(
            album.mbid,
            album.name,
            Artist("", album.artist),
            imageMapper.getMainImageUrl(album.images),
            trackMapper.transform(album.tracks.tracks)
        )
    }

    fun transform(album: LastFmAlbum) = album.mbid?.let {
        Album(
            album.mbid,
            album.name,
            artistMapper.transform(album.artist),
            imageMapper.getMainImageUrl(album.images),
            trackMapper.transform(album.tracks?.tracks)
        )
    }
}