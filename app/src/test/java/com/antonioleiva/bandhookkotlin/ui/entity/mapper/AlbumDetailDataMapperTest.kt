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

package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AlbumDetailDataMapperTest {

    lateinit var album: Album

    lateinit var albumDetailDataMapper: AlbumDetailDataMapper

    @Before
    fun setUp() {
        album = Album("album id", "album name", Artist("artist id", "artist name"), "album url", emptyList())

        albumDetailDataMapper = AlbumDetailDataMapper()
    }

    @Test
    fun testTransform() {
        // When
        val transformedAlbum = albumDetailDataMapper.transform(album)

        // Then
        assertNotNull(transformedAlbum)
        assertEquals(album.id, transformedAlbum?.id)
        assertEquals(album.name, transformedAlbum?.name)
        assertEquals(album.url, transformedAlbum?.url)
        assertEquals(album.tracks, transformedAlbum?.tracks)
    }

    @Test
    fun testTransform_null() {
        // When
        val transformedAlbum = albumDetailDataMapper.transform(null)

        // Then
        assertNull(transformedAlbum)
    }
}