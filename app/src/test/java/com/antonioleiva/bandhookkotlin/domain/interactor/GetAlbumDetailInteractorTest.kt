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
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAlbumDetailInteractorTest {

    @Mock
    lateinit var albumRepository: AlbumRepository

    lateinit var getAlbumDetailInteractor: GetAlbumDetailInteractor

    private val albumId = "album id"

    @Before
    fun setUp() {
        `when`(albumRepository.getAlbum(albumId)).thenReturn(Album("album id", "album name",
                Artist("artist id", "artist name", null, null, null), "album url", emptyList()))

        getAlbumDetailInteractor = GetAlbumDetailInteractor(albumRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun testInvoke_withoutId() {
        // When
        getAlbumDetailInteractor.invoke()

        // Then expected illegal state exception
    }

    @Test
    fun testInvoke_withId() {
        // Given
        getAlbumDetailInteractor.albumId = albumId

        // When
        val event = getAlbumDetailInteractor.invoke()

        // Then
        assertEquals(AlbumEvent::class.java, event.javaClass)
        assertEquals(albumId, (event as AlbumEvent).album?.id)
    }
}