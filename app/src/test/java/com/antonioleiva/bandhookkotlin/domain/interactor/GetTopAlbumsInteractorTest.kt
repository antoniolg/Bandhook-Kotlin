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

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.interactor.event.TopAlbumsEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTopAlbumsInteractorTest {

    @Mock
    lateinit var albumRepository: AlbumRepository

    lateinit var album: Album

    lateinit var getTopAlbumsInteractor: GetTopAlbumsInteractor

    private val artistId = "artist id"
    private val artistName = "artist name"

    @Before
    fun setUp() {
        album = Album("album id", "Album name", Artist("artist id", "artist name"), null, emptyList())

        `when`(albumRepository.getTopAlbums(null, artistName)).thenReturn(listOf(album))
        `when`(albumRepository.getTopAlbums(artistId, null)).thenReturn(listOf(album))

        getTopAlbumsInteractor = GetTopAlbumsInteractor(albumRepository)
    }

    @Test
    fun testInvoke_withArtistId() {
        // Given
        getTopAlbumsInteractor.artistId = artistId

        // When
        val event = getTopAlbumsInteractor.invoke()

        // Then
        Assert.assertEquals(TopAlbumsEvent::class.java, event.javaClass)
        Assert.assertEquals(album, (event as TopAlbumsEvent).topAlbums[0])
    }

    @Test
    fun testInvoke_withArtistName() {
        // Given
        getTopAlbumsInteractor.artistName = artistName

        // When
        val event = getTopAlbumsInteractor.invoke()

        // Then
        Assert.assertEquals(TopAlbumsEvent::class.java, event.javaClass)
        Assert.assertEquals(album, (event as TopAlbumsEvent).topAlbums[0])
    }

    @Test(expected = IllegalStateException::class)
    fun testInvoke_withoutData() {
        // When
        getTopAlbumsInteractor.invoke()

        // Then expected illegal state exception
    }
}